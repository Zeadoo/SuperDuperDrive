package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/signup")
public class UserSignupController {

    private final UserService userSignupService;


    public UserSignupController(UserService userService) {
        this.userSignupService = userService;
    }


    @GetMapping()
    public String signupView() {
        return "signup";
    }

    
    @PostMapping()
    public String signupUser(@ModelAttribute User user, Model model, HttpSession session) {
        String signupError = null;

        if (!this.userSignupService.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already registered.";
        }

        if (signupError == null) {
            int rowsAdded = this.userSignupService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError == null) {
            session.setAttribute("signupSuccess", true);
            return "redirect:/login?success";
        } else {
            model.addAttribute("signupError", signupError);
            return "signup";
        }


    }
}
