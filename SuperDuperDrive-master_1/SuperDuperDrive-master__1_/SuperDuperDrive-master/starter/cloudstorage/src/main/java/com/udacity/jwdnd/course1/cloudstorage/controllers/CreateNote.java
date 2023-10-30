package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note")
public class CreateNote {
    private final UserService user;
    private final NoteService createNoteService;

    public CreateNote(UserService user, NoteService note) {
        this.user = user;
        this.createNoteService = note;
    }

    @PostMapping
    public String postNote(Model model, Note note) {
        try {
            if (note.getNoteId() != null) {
                return "forward:/note/update";
            }
            User currUser = user.getUser(
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName());

            this.createNoteService.addNote(note, currUser.getUserId());
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }

    }
}
