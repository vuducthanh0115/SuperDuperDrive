package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping
public class HomeController {

    private final FileService fileService;

    private final NoteService noteService;

    private final EncryptionService encryptionService;

    private final CredentialService credentialService;

    @GetMapping("/home")
    public String home(Model model) {
        // 1. Check authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            try {
                model.addAttribute("files", fileService.getAllFileByLoggedUser());
                model.addAttribute("notes", noteService.getAllNotesByLoggedUser());
                model.addAttribute("credentials", credentialService.getAllCredentials());
                model.addAttribute("encryptionService", encryptionService);
            } catch (InternalException i) {
                model.addAttribute("error", i.toString());
            }
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}
