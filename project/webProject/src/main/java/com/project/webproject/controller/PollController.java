package com.project.webproject.controller;

import com.project.webproject.service.PollCommentService;
import com.project.webproject.service.PollOptionService;
import com.project.webproject.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PollController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private PollService pollService;

    @Autowired
    private PollOptionService pollOptionService;

    @Autowired
    private PollCommentService pollCommentService;

    @GetMapping("/poll/{id}")
    public String pollPage(@PathVariable("id") String id, Model model) {
        logger.debug("Handling GET /poll/{}", id);
        var poll = pollService.getPoll(id);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", id);
            model.addAttribute("error", "Poll not found");
            return "error";
        }
        model.addAttribute("pollQuestion", poll.getQuestion());
        model.addAttribute("options", pollOptionService.getOptionsByPollId(id));
        model.addAttribute("comments", pollCommentService.getCommentsByPollId(id));
        logger.debug("Returning view: poll");
        return "poll";
    }
}