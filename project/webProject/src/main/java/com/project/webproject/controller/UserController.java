package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.model.Comment;
import com.project.webproject.model.PollComment;
import com.project.webproject.model.Vote;
import com.project.webproject.service.AppUserService;
import com.project.webproject.service.CommentService;
import com.project.webproject.service.PollCommentService;
import com.project.webproject.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PollCommentService pollCommentService;

    @Autowired
    private CommentService commentService;

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

    @GetMapping("/voting-history")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String showVotingHistory(Model model) {
        logger.debug("Handling GET /voting-history");
        List<Vote> votes = voteService.getAllVotes();
        model.addAttribute("votes", votes);
        return "votingHistory";
    }

    @GetMapping("/comment-history")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String showCommentHistory(Model model) {
        logger.debug("Handling GET /comment-history");
        List<PollComment> pollComments = pollCommentService.getAllComments();
        List<Comment> lectureComments = commentService.getAllComments();
        model.addAttribute("pollComments", pollComments != null ? pollComments : new ArrayList<>());
        model.addAttribute("lectureComments", lectureComments != null ? lectureComments : new ArrayList<>());
        return "commentHistory";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        logger.debug("Handling GET /users");
        try {
            List<AppUser> users = appUserService.getAllUsers();
            model.addAttribute("users", users != null ? users : new ArrayList<>());
        } catch (Exception e) {
            logger.error("Error retrieving users", e);
            model.addAttribute("errorMessage", "Unable to retrieve users: " + e.getMessage());
        }
        return "users";
    }

    @PostMapping("/users/add")
    public String addUser(@RequestParam String username, @RequestParam String password, @RequestParam String role,
                          @RequestParam String fullName, @RequestParam String email, @RequestParam String phoneNumber,
                          RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /users/add, username={}, role={}", username, role);
        try {
            appUserService.addUser(username, password, role, fullName, email, phoneNumber);
            redirectAttributes.addFlashAttribute("successMessage", "User added successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to add user: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam String username, @RequestParam(required = false) String password,
                             @RequestParam(required = false) String role, @RequestParam(required = false) String fullName,
                             @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /users/update, username={}", username);
        try {
            appUserService.updateUser(username, password, role, fullName, email, phoneNumber);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to update user: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam String username, @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /users/delete, username={}", username);
        if (username.equals(userDetails.getUsername())) {
            logger.warn("Attempt to delete self: {}", username);
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete yourself");
            return "redirect:/users";
        }
        try {
            appUserService.deleteUser(username);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.warn("Failed to delete user: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/users";
    }
}