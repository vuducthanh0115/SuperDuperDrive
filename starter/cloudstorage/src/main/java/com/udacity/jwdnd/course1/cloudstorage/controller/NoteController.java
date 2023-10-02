package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.exception.InternalException;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping
public class NoteController {

    private final NoteService noteService;

    @PostMapping("add-update-note")
    public String addNoteOrUpdateNote(@ModelAttribute("noteInfo") Note noteInfo, Model model) {
        String textError = null;
        try {
            noteService.createNoteOrUpdateNote(noteInfo);
        } catch (InternalException i) {
            textError = i.getMessage();
        } catch (Exception e) {
            textError = "Add note failed";
        }

        if (textError == null) {
            model.addAttribute("success", "Your changes was successfully saved");
        } else {
            model.addAttribute("error", textError);
        }
        return "result";
    }

    @GetMapping("/delete-note")
    public String deleteFile(@RequestParam("noteId") Long noteId, Model model, Authentication authentication) {
        try {
            noteService.deleteNoteByNoteIdAndUserId(noteId);
            model.addAttribute("success", "Delete successfully");
        } catch (InternalException i) {
            model.addAttribute("error", i.getMessage());
        }
        return "result";
    }
}
