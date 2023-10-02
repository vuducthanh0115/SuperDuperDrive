package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface FileService {
    boolean uploadFile(MultipartFile multipartFile) throws InternalException, IOException;

    List<File> getAllFileByLoggedUser() throws InternalException;

    File getFile(Long fileId) throws InternalException;

    void deleteFile(Long fileId) throws InternalException;

}
