package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/file/delete")
public class DeleteFile {
    private final FileService deleteFileService;
    private final UserService user;

    public DeleteFile(UserService user, FileService file) {
        this.user = user;
        this.deleteFileService = file;
    }

    @GetMapping
    public String deleteController(@RequestParam("fileid") int fileId) {
        try {
            File file = this.deleteFileService.getFileUsingId(fileId);

            User currUser = user.getUser(
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .getName());

            if (!Objects.equals(currUser.getUserId(), file.getUserId())) {
                return "redirect:/result?success=0";
            }
            this.deleteFileService.deleteFileFromMapper(fileId);


            return "redirect:/result?success=1";
        } catch (Exception e) {
            return "redirect:/result?success=0";
        }

    }
}
