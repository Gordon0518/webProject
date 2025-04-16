package com.project.webproject.controller;

import com.project.webproject.service.CommentService;
import com.project.webproject.service.LectureNoteService;
import com.project.webproject.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureNoteService lectureNoteService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/lecture/{id}")
    public String lectureMaterial(@PathVariable("id") String id, Model model) {
        logger.debug("Handling GET /lecture/{}", id);
        var lecture = lectureService.getLecture(id);
        if (lecture == null) {
            logger.warn("Lecture not found for id: {}", id);
            model.addAttribute("error", "Lecture not found");
            return "error";
        }
        model.addAttribute("lectureTitle", lecture.getTitle());
        model.addAttribute("notes", lectureNoteService.getNotesByLectureId(id));
        model.addAttribute("comments", commentService.getCommentsByLectureId(id));
        logger.debug("Returning view: lecture");
        return "lecture";
    }
}