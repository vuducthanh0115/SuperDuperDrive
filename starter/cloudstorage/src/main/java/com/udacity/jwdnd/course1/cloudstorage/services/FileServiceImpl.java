package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    private final CommonService commonService;

    @Override
    public boolean uploadFile(MultipartFile multipartFile) throws InternalException, IOException {

        // 1. Validate the input data
        if (multipartFile.isEmpty()) {
            throw new InternalException("The file upload is empty");
        }

        if (multipartFile.getSize() > 5242880) {
            throw new MaxUploadSizeExceededException(multipartFile.getSize());
        }

        // Get the user info of a logged-in user and if it does not exist, throw an exception
        User user = commonService.getUserByUsername();

        File file = File.builder()
                .userId(user.getUserId())
                .fileData(multipartFile.getBytes())
                .fileName(multipartFile.getOriginalFilename())
                .fileSize(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .build();

        // Check if the file is already exists
        Optional<File> checkFileExist = fileMapper.getFileByNameAndUserId(file.getFileName(), user.getUserId());

        if (checkFileExist.isEmpty()) {
            // 2. Save the file
            return fileMapper.addFile(file) > 0;
        } else {
            throw new InternalException("The file already exists.");
        }
    }

    @Override
    public List<File> getAllFileByLoggedUser() throws InternalException {

        return fileMapper.getAllFiles(commonService.getUserByUsername().getUserId());
    }

    @Override
    public File getFile(Long fileId) throws InternalException {

        Optional<File> file = fileMapper.getFileByIdAndUserId(fileId, commonService.getUserByUsername().getUserId());

        if (file.isPresent()) {
            return file.get();
        } else {
            throw new InternalException("File not exists.");
        }
    }

    @Override
    public void deleteFile(Long fileId) throws InternalException {

        Optional<File> file = fileMapper.getFileByIdAndUserId(fileId, commonService.getUserByUsername().getUserId());

        if (file.isPresent()) {
            fileMapper.deleteFile(fileId, commonService.getUserByUsername().getUserId());
        } else {
            throw new InternalException("File not exists.");
        }
    }
}
