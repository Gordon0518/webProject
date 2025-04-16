package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
}