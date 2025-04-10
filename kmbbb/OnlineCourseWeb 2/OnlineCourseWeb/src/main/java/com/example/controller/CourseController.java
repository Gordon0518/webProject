package com.example.controller;

import com.example.model.*;
import com.example.service.CourseService;
import com.example.service.UserService;
import com.example.service.VoteService;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private CommentService commentService;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).orElse(null);
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = getCurrentUser();
        if (user == null) return "redirect:/OnlineCourseWeb/login";
        if (user.getRole().equals("ROLE_TEACHER")) {
            model.addAttribute("courses", courseService.findCoursesByTeacher(user));
        } else {
            model.addAttribute("courses", courseService.findAllCourses());
        }
        model.addAttribute("user", user);
        return "dashboard";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam String title) {
        User user = getCurrentUser();
        if (user != null && user.getRole().equals("ROLE_TEACHER")) {
            courseService.saveCourse(new Course(title, user));
        }
        return "redirect:/OnlineCourseWeb/dashboard";
    }

    @GetMapping("/courses/{id}")
    public String viewCourse(@PathVariable Long id, Model model) {
        User user = getCurrentUser();
        if (user == null) return "redirect:/OnlineCourseWeb/login";
        Course course = courseService.findAllCourses().stream()
                .filter(c -> c.getId().equals(id)).findFirst().orElse(null);
        if (course == null) return "redirect:/OnlineCourseWeb/dashboard";
        model.addAttribute("course", course);
        model.addAttribute("materials", courseService.findMaterialsByCourse(course));
        model.addAttribute("comments", courseService.findMaterialsByCourse(course).stream()
                .flatMap(m -> commentService.findCommentsByMaterial(m).stream()).toList());
        model.addAttribute("user", user);
        return "materials";
    }

    @PostMapping("/materials/add")
    public String addMaterial(@RequestParam Long courseId, @RequestParam String fileName) {
        User user = getCurrentUser();
        if (user != null && user.getRole().equals("ROLE_TEACHER")) {
            Course course = courseService.findAllCourses().stream()
                    .filter(c -> c.getId().equals(courseId)).findFirst().orElse(null);
            if (course != null) {
                courseService.saveMaterial(new CourseMaterial(course, fileName, "/uploads/" + fileName));
            }
        }
        return "redirect:/OnlineCourseWeb/courses/" + courseId;
    }

    @PostMapping("/vote")
    public String vote(@RequestParam Long materialId, @RequestParam int voteValue) {
        User user = getCurrentUser();
        if (user != null && user.getRole().equals("ROLE_STUDENT")) {
            CourseMaterial material = courseService.findAllCourses().stream()
                    .flatMap(c -> courseService.findMaterialsByCourse(c).stream())
                    .filter(m -> m.getId().equals(materialId)).findFirst().orElse(null);
            if (material != null) {
                voteService.saveVote(new Vote(user, material, voteValue));
            }
            return "redirect:/OnlineCourseWeb/courses/" + material.getCourse().getId();
        }
        return "redirect:/OnlineCourseWeb/dashboard";
    }

    @PostMapping("/comment")
    public String comment(@RequestParam Long materialId, @RequestParam String content) {
        User user = getCurrentUser();
        if (user != null && user.getRole().equals("ROLE_STUDENT")) {
            CourseMaterial material = courseService.findAllCourses().stream()
                    .flatMap(c -> courseService.findMaterialsByCourse(c).stream())
                    .filter(m -> m.getId().equals(materialId)).findFirst().orElse(null);
            if (material != null) {
                commentService.saveComment(new Comment(user, material, content, LocalDateTime.now()));
            }
            return "redirect:/OnlineCourseWeb/courses/" + material.getCourse().getId();
        }
        return "redirect:/OnlineCourseWeb/dashboard";
    }
}