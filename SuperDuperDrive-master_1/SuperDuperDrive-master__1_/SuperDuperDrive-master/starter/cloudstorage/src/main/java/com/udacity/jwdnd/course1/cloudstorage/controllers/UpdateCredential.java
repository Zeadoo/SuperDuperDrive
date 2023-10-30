package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential/update")
public class UpdateCredential {
    private final UserCredentialsService updateUserCredentialsService;

    public UpdateCredential(UserCredentialsService userCredentialsService) {
        this.updateUserCredentialsService = userCredentialsService;
    }

    @PostMapping
    public String postUpdateCredential(Model model, Credential credential) {
        try {
            updateUserCredentialsService.updateCredential(credential);
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }

    }

}
