package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.UserCredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
@RequestMapping("/credential/delete")
public class DeleteCredential {
    private final UserService user;
    private final UserCredentialsService deleteUserCredentialsService;

    public DeleteCredential(CredentialMapper credentialMapper, UserService user, UserCredentialsService credential) {
        this.user = user;
        this.deleteUserCredentialsService = credential;
    }

    @GetMapping
    public String deleteCredential(@RequestParam("credid") Integer credentialId) {
        Credential credential = this.deleteUserCredentialsService.getCredentialUsingId(credentialId);

        User currentUser = user.getUser(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName());

        if (!Objects.equals(currentUser.getUserId(), credential.getUserid())) {
            return "redirect:/result?success=0";
        }
        try {
            this.deleteUserCredentialsService.deleteCurrentCredential(credentialId);
            return "redirect:/result?success=1";
        } catch (Error e) {
            return "redirect:/result?success=0";
        }
    }

}
