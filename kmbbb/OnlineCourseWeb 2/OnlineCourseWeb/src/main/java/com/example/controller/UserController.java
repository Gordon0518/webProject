package com.example.controller;

import com.example.model.User;
import com.example.service.VoteService;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    private VoteService voteService;
    @Autowired
    private CommentService commentService;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return voteService.findVotesByUser(new User(username, "", "")).isEmpty() ? null :
                voteService.findVotesByUser(new User(username, "", "")).get(0).getUser();
    }

    @GetMapping("/votingHistory")
    public String votingHistory(Model model) {
        User user = getCurrentUser();
        if (user == null) return "redirect:/OnlineCourseWeb/login";
        model.addAttribute("votes", voteService.findVotesByUser(user));
        model.addAttribute("user", user);
        return "votingHistory";
    }

    @GetMapping("/commentHistory")
    public String commentHistory(Model model) {
        User user = getCurrentUser();
        if (user == null) return "redirect:/OnlineCourseWeb/login";
        model.addAttribute("comments", commentService.findCommentsByUser(user));
        model.addAttribute("user", user);
        return "commentHistory";
    }
}