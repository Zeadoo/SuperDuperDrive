package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.FileInfo;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/home")
public class UserHomeController {
    private final NoteService homeNoteService;
    private final UserCredentialsService homeUserCredentialsService;
    private final FileService homeFileService;
    private final UserService homeUserService;

    public UserHomeController(NoteService note, UserCredentialsService credential, FileService file, UserService user) {
        this.homeNoteService = note;
        this.homeUserCredentialsService = credential;
        this.homeFileService = file;
        this.homeUserService = user;
    }

    
    @GetMapping
    public String getHome(Model model) {

        User currUser = homeUserService.getUser(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        List<Credential> listOfCredential = homeUserCredentialsService.getCredentialaAsList(currUser.getUserId());
        model.addAttribute("Credentials", listOfCredential);

        List<FileInfo> listOfFiles = new ArrayList<>(homeFileService.getFileAsList(currUser.getUserId()));
        model.addAttribute("Files", listOfFiles);

        List<Note> listOfNotes = homeNoteService.getNoteAsList(currUser.getUserId());
        model.addAttribute("Notes", listOfNotes);

        return "home";
    }

}
