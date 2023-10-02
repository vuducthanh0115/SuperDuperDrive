package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping
@AllArgsConstructor
public class FileController {
    private FileService fileService;

    @PostMapping("upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        String textError = null;
        try {
            fileService.uploadFile(file);
        } catch (InternalException i) {
            textError = i.getMessage();
        } catch (IOException io) {
            textError = "Upload was unsuccessful. Please try again";
        } catch (MaxUploadSizeExceededException max) {
            textError = "Large File upload failed";
        } catch (Exception e) {
            textError = "Upload failed";
        }

        if (textError == null) {
            model.addAttribute("success", "Upload successfully");
        } else {
            model.addAttribute("error", textError);
        }
        return "result";
    }

    @GetMapping("/download")
    public ResponseEntity downloadFile(@RequestParam("fileId") Long fileId, Model model) {
        try {
            File file = fileService.getFile(fileId);

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"").contentLength(file.getFileData().length).contentType(MediaType.APPLICATION_OCTET_STREAM).body(file.getFileData());
        } catch (InternalException i) {
            model.addAttribute("error", "File not found");
        }
        return null;
    }

    @GetMapping("/delete-file")
    public String deleteFile(@RequestParam("fileId") Long fileId, Model model) {
        try {
            fileService.deleteFile(fileId);
            model.addAttribute("success", "Delete successfully");
        } catch (InternalException i) {
            model.addAttribute("error", "Delete was unsuccessful");
        }
        return "result";
    }
}
