package com.project.webproject.controller;

import com.project.webproject.service.CourseService;
import com.project.webproject.service.LectureService;
import com.project.webproject.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private LectureService lectureService;

    @Autowired
    private PollService pollService;

    @GetMapping("/")
    public String index(Model model) {
        Long courseId = 1L;
        var course = courseService.getCourse(courseId);
        model.addAttribute("courseName", course != null ? course.getName() : "Course Not Found");
        model.addAttribute("lectures", lectureService.getLecturesByCourseId(courseId));
        model.addAttribute("polls", pollService.getPollsByCourseId(courseId));
        return "index";
    }
}