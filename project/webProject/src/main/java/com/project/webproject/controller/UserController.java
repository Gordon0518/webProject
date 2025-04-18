package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.debug("Handling GET /register");
        model.addAttribute("user", new AppUser());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") AppUser user, Model model) {
        logger.debug("Handling POST /register for username: {}", user.getUsername());
        if (appUserService.findByUsername(user.getUsername()) != null) {
            logger.warn("Username already exists: {}", user.getUsername());
            model.addAttribute("error", "Username already exists");
            return "register";
        }
        user.setRole("ROLE_STUDENT");
        appUserService.registerUser(user);
        logger.debug("User registered: {}", user.getUsername());
        return "redirect:/login?success=true";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.debug("Handling GET /login");
        return "login";
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        logger.debug("Handling GET /profile for user: {}", userDetails.getUsername());
        AppUser user = appUserService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam String phoneNumber,
                                @RequestParam(required = false) String password) {
        logger.debug("Handling POST /profile for user: {}", userDetails.getUsername());
        appUserService.updateUserProfile(userDetails.getUsername(), fullName, email, phoneNumber, password);
        return "redirect:/profile?success=true";
    }


}