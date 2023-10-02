package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping
public class CredentialController {
    private CredentialService credentialService;

    @PostMapping("/add-edit-credential")
    public String addUpdateCredential(@ModelAttribute("credential") Credentials credential, Model model) {
        String textError = null;
        try {
            credentialService.createOrUpdateCredential(credential);
        } catch (InternalException i) {
            textError = i.getMessage();
        } catch (Exception e) {
            textError = "Your changes was unsuccessfully. Pls try again.";
        }
        if (textError == null) {
            model.addAttribute("success", "Your changes was successfully saved.");
        } else {
            model.addAttribute("error", textError);
        }
        return "result";
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(@RequestParam("credentialId") Long credentialId, Model model) {
        String textError = null;
        try {
            credentialService.deleteCredential(credentialId);
        } catch (InternalException i) {
            textError = i.getMessage();
        } catch (Exception e) {
            textError = "Delete was unsuccessfully";
        }
        if (textError == null) {
            model.addAttribute("success","Delete  was successfully");
        } else {
            model.addAttribute("error", textError);
        }
        return "result";
    }
}
