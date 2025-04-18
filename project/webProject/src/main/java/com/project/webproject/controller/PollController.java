package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.model.Course;
import com.project.webproject.model.Poll;
import com.project.webproject.model.PollOption;
import com.project.webproject.service.PollCommentService;
import com.project.webproject.service.PollOptionService;
import com.project.webproject.service.PollService;
import com.project.webproject.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class PollController {

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);

    @Autowired
    private PollService pollService;

    @Autowired
    private PollOptionService pollOptionService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PollCommentService pollCommentService;

    @GetMapping("/poll/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String showAddPollForm(Model model) {
        logger.debug("Handling GET /poll/add");
        return "addPoll";
    }

    @PostMapping("/poll/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String addPoll(@RequestParam("courseId") Long courseId,
                          @RequestParam("question") String question,
                          @RequestParam("options") List<String> options,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/add for courseId: {}, question: {}", courseId, question);
        try {
            if (options.size() != 4) {
                logger.warn("Invalid number of options provided: {}", options.size());
                model.addAttribute("errorMessage", "Exactly four options are required.");
                return "addPoll";
            }

            Poll poll = new Poll();
            poll.setId(UUID.randomUUID().toString());
            poll.setQuestion(question);
            poll.setCourse(new Course(courseId, null));
            pollService.savePoll(poll);

            List<PollOption> pollOptions = new ArrayList<>();
            for (String optionText : options) {
                if (optionText != null && !optionText.trim().isEmpty()) {
                    PollOption pollOption = new PollOption();
                    pollOption.setId(UUID.randomUUID().toString());
                    pollOption.setOptionText(optionText.trim());
                    pollOption.setPoll(poll);
                    pollOption.setVoteCount(0);
                    pollOptions.add(pollOption);
                }
            }

            if (pollOptions.size() != 4) {
                logger.warn("Invalid options after trimming: {}", pollOptions.size());
                model.addAttribute("errorMessage", "All four options must be non-empty.");
                return "addPoll";
            }

            pollOptionService.saveAll(pollOptions);
            redirectAttributes.addFlashAttribute("successMessage", "Poll added successfully");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error adding poll", e);
            model.addAttribute("errorMessage", "Failed to add poll: " + e.getMessage());
            return "addPoll";
        }
    }

    @GetMapping("/poll/{id}")
    public String showPoll(@PathVariable("id") String id, Model model, Authentication authentication) {
        logger.debug("Handling GET /poll/{}", id);
        try {
            Poll poll = pollService.getPoll(id);
            if (poll == null) {
                logger.warn("Poll not found for id: {}", id);
                model.addAttribute("error", "Poll not found");
                return "error";
            }

            model.addAttribute("pollQuestion", poll.getQuestion());
            model.addAttribute("pollId", poll.getId());
            model.addAttribute("options", pollOptionService.getOptionsByPollId(id));
            model.addAttribute("comments", pollCommentService.getCommentsByPollId(id));

            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                model.addAttribute("userVote", voteService.getVoteByVoterAndPoll(username, id));
            }

            return "poll";
        } catch (Exception e) {
            logger.error("Error displaying poll: {}", id, e);
            model.addAttribute("error", "Failed to load poll: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/poll/{id}/vote")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public String castVote(@PathVariable("id") String id,
                           @RequestParam(value = "optionId", required = false) String optionId,
                           Authentication authentication,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/vote", id);
        try {
            String username = authentication.getName();
            AppUser user = new AppUser();
            user.setUsername(username);
            voteService.castVote(id, optionId, user);
            redirectAttributes.addFlashAttribute("successMessage", "Vote submitted successfully");
            return "redirect:/poll/" + id;
        } catch (Exception e) {
            logger.error("Error casting vote for poll: {}", id, e);
            model.addAttribute("error", "Failed to submit vote: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/poll/{id}/comment")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public String addComment(@PathVariable("id") String id,
                             @RequestParam("content") String content,
                             Authentication authentication,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/comment", id);
        try {
            String username = authentication.getName();
            AppUser user = new AppUser();
            user.setUsername(username);
            pollCommentService.savePollComment(id, content, user);
            redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully");
            return "redirect:/poll/" + id;
        } catch (Exception e) {
            logger.error("Error adding comment to poll: {}", id, e);
            model.addAttribute("error", "Failed to add comment: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/poll/{id}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String deletePoll(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/delete", id);
        try {
            Poll poll = pollService.getPoll(id);
            if (poll == null) {
                logger.warn("Poll not found for id: {}", id);
                model.addAttribute("error", "Poll not found");
                return "error";
            }
            pollService.deletePoll(id);
            redirectAttributes.addFlashAttribute("successMessage", "Poll deleted successfully");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error deleting poll: {}", id, e);
            model.addAttribute("error", "Failed to delete poll: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/poll/{pollId}/comment/{commentId}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String deletePollComment(@PathVariable("pollId") String pollId,
                                    @PathVariable("commentId") String commentId,
                                    Model model,
                                    RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /poll/{}/comment/{}/delete", pollId, commentId);
        try {
            Poll poll = pollService.getPoll(pollId);
            if (poll == null) {
                logger.warn("Poll not found for id: {}", pollId);
                model.addAttribute("error", "Poll not found");
                return "error";
            }
            pollCommentService.deletePollComment(commentId);
            redirectAttributes.addFlashAttribute("successMessage", "Comment deleted successfully");
            return "redirect:/poll/" + pollId;
        } catch (Exception e) {
            logger.error("Error deleting comment: {} for poll: {}", commentId, pollId, e);
            model.addAttribute("error", "Failed to delete comment: " + e.getMessage());
            return "error";
        }
    }
}