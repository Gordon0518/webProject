package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.service.AppUserService;
import com.project.webproject.service.PollCommentService;
import com.project.webproject.service.PollOptionService;
import com.project.webproject.service.PollService;
import com.project.webproject.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PollController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private PollService pollService;

    @Autowired
    private PollOptionService pollOptionService;

    @Autowired
    private PollCommentService pollCommentService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/poll/{id}")
    public String pollPage(@PathVariable("id") String id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        logger.debug("Handling GET /poll/{}", id);
        var poll = pollService.getPoll(id);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", id);
            model.addAttribute("error", "Poll not found");
            return "error";
        }
        var options = pollOptionService.getOptionsByPollId(id);
        var comments = pollCommentService.getCommentsByPollId(id);
        var userVote = userDetails != null ? voteService.getVoteByVoterAndPoll(userDetails.getUsername(), id) : null;

        logger.debug("Poll: {}, Options: {}, Comments: {}, UserVote: {}",
                poll.getQuestion(), options.size(), comments.size(), userVote != null ? userVote.getId() : "null");

        model.addAttribute("pollId", id);
        model.addAttribute("pollQuestion", poll.getQuestion());
        model.addAttribute("options", options);
        model.addAttribute("comments", comments);
        model.addAttribute("userVote", userVote);
        logger.debug("Returning view: poll");
        return "poll";
    }

    @PostMapping("/poll/{id}/comment")
    public String addPollComment(@PathVariable("id") String id, @RequestParam String content,
                                 @AuthenticationPrincipal UserDetails userDetails, Model model,
                                 RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/comment", id);
        if (id == null || id.isEmpty()) {
            logger.warn("Invalid poll ID: {}", id);
            model.addAttribute("error", "Invalid poll ID");
            return "error";
        }
        var poll = pollService.getPoll(id);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", id);
            model.addAttribute("error", "Poll not found");
            return "error";
        }
        AppUser user = appUserService.findByUsername(userDetails.getUsername());
        pollCommentService.savePollComment(id, content, user);
        redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully");
        return "redirect:/poll/" + id;
    }

    @PostMapping("/poll/{id}/vote")
    public String castVote(@PathVariable("id") String id, @RequestParam(required = false) String optionId,
                           @AuthenticationPrincipal UserDetails userDetails, Model model,
                           RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/vote", id);
        if (id == null || id.isEmpty()) {
            logger.warn("Invalid poll ID: {}", id);
            model.addAttribute("error", "Invalid poll ID");
            return "error";
        }
        var poll = pollService.getPoll(id);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", id);
            model.addAttribute("error", "Poll not found");
            return "error";
        }
        AppUser user = appUserService.findByUsername(userDetails.getUsername());
        voteService.castVote(id, optionId, user);
        redirectAttributes.addFlashAttribute("successMessage", "Vote submitted successfully");
        return "redirect:/poll/" + id;
    }

    @PostMapping("/poll/{id}/undo-vote")
    public String undoVote(@PathVariable("id") String id, @AuthenticationPrincipal UserDetails userDetails,
                           Model model, RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/undo-vote", id);
        if (id == null || id.isEmpty()) {
            logger.warn("Invalid poll ID: {}", id);
            model.addAttribute("error", "Invalid poll ID");
            return "error";
        }
        var poll = pollService.getPoll(id);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", id);
            model.addAttribute("error", "Poll not found");
            return "error";
        }
        AppUser user = appUserService.findByUsername(userDetails.getUsername());
        voteService.castVote(id, null, user);
        redirectAttributes.addFlashAttribute("successMessage", "Vote successfully undone");
        return "redirect:/poll/" + id;
    }
}