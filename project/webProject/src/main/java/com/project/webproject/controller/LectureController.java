package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.service.AppUserService;
import com.project.webproject.service.CommentService;
import com.project.webproject.service.LectureNoteService;
import com.project.webproject.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureNoteService lectureNoteService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/lecture/{id}")
    public String lectureMaterial(@PathVariable("id") String id, Model model) {
        logger.debug("Handling GET /lecture/{}", id);
        var lecture = lectureService.getLecture(id);
        if (lecture == null) {
            logger.warn("Lecture not found for id: {}", id);
            model.addAttribute("error", "Lecture not found");
            return "error";
        }
        model.addAttribute("lectureId", id);
        model.addAttribute("lectureTitle", lecture.getTitle());
        model.addAttribute("notes", lectureNoteService.getNotesByLectureId(id));
        model.addAttribute("comments", commentService.getCommentsByLectureId(id));
        logger.debug("Returning view: lecture");
        return "lecture";
    }

    @PostMapping("/lecture/{id}/comment")
    public String addComment(@PathVariable("id") String id, @RequestParam String content,
                             @AuthenticationPrincipal UserDetails userDetails, Model model) {
        logger.debug("Handling POST /lecture/{}/comment", id);
        if (id == null || id.isEmpty()) {
            logger.warn("Invalid lecture ID: {}", id);
            model.addAttribute("error", "Invalid lecture ID");
            return "error";
        }
        var lecture = lectureService.getLecture(id);
        if (lecture == null) {
            logger.warn("Lecture not found for id: {}", id);
            model.addAttribute("error", "Lecture not found");
            return "error";
        }
        AppUser user = appUserService.findByUsername(userDetails.getUsername());
        commentService.saveComment(id, content, user);
        return "redirect:/lecture/" + id;
    }
}