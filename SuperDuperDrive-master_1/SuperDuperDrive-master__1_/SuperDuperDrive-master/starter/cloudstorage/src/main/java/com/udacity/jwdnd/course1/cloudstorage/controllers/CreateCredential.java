package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CreateCredential {
    private final UserService user;
    private final UserCredentialsService createUserCredentialsService;

    public CreateCredential(UserService user, UserCredentialsService credential) {
        this.user = user;
        this.createUserCredentialsService = credential;
    }

    @PostMapping
    public String postCredential(Model model, Credential credential) {
        if (credential.getCredentialId() != null) {
            return "forward:/credential/update";
        }
        User currUser = user.getUser(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());
        try {
            this.createUserCredentialsService.addCredential(credential, currUser.getUserId());
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }

    }
}
