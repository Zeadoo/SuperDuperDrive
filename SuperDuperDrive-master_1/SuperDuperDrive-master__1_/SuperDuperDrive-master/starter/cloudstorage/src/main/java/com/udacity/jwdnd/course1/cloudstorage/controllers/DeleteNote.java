package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/note/delete")
public class DeleteNote {
    private final UserService user;
    private final NoteService deleteNoteService;

    public DeleteNote( UserService user, NoteService note) {
        this.user = user;
        this.deleteNoteService = note;
    }

    @GetMapping
    public String getDeleteNote(@RequestParam("noteId") Integer noteId) {
        try {
            Note note = this.deleteNoteService.getNoteUsingId(noteId);

            User currUser = user.getUser(
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName());

            if (!Objects.equals(currUser.getUserId(), note.getUserId())) {
                return "redirect:/result?success=0";
            }
            this.deleteNoteService.deleteNote(noteId);
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }
    }
}
