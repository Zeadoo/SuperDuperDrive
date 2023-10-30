package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/note/update")
public class UpdateNote {


    private final NoteService updateNoteService;

    public UpdateNote(NoteService noteService) {
        this.updateNoteService = noteService;
    }

    @PostMapping
    public String postUpdateNote(Model model, Note note) {
        try {
            updateNoteService.updateNote(note);
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }

    }

}
