package com.project.webproject.controller;

import com.project.webproject.service.CourseService;
import com.project.webproject.service.LectureService;
import com.project.webproject.service.PollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private CourseService courseService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private PollService pollService;

    @GetMapping("/")
    public String index(Model model) {
        logger.debug("Handling GET /");
        var course = courseService.getCourse(1L);
        if (course == null) {
            logger.warn("Course not found for id: 1");
            model.addAttribute("error", "Course not found");
            return "error";
        }
        model.addAttribute("courseName", course.getName());
        model.addAttribute("lectures", lectureService.getLecturesByCourseId(1L));
        model.addAttribute("polls", pollService.getPollsByCourseId(1L));
        logger.debug("Returning view: index");
        return "index";
    }
}