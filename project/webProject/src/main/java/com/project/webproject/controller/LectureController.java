package com.project.webproject.controller;

import com.project.webproject.model.AppUser;
import com.project.webproject.model.Comment;
import com.project.webproject.model.Course;
import com.project.webproject.model.Lecture;
import com.project.webproject.service.CommentService;
import com.project.webproject.service.LectureNoteService;
import com.project.webproject.service.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class LectureController {

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    @Autowired
    private LectureService lectureService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LectureNoteService lectureNoteService;

    @GetMapping("/lecture/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String showAddLectureForm(Model model) {
        logger.debug("Handling GET /lecture/add");
        return "addLecture";
    }

    @PostMapping("/lecture/add")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String addLecture(@RequestParam("courseId") Long courseId,
                             @RequestParam("title") String title,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /lecture/add for courseId: {}, title: {}", courseId, title);
        try {
            Lecture lecture = new Lecture();
            lecture.setId(UUID.randomUUID().toString());
            lecture.setTitle(title);
            lecture.setCourse(new Course(courseId, null));
            lectureService.saveLecture(lecture);
            redirectAttributes.addFlashAttribute("successMessage", "Lecture added successfully");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error adding lecture", e);
            model.addAttribute("errorMessage", "Failed to add lecture: " + e.getMessage());
            return "addLecture";
        }
    }

    @GetMapping("/lecture/{id}")
    public String showLecture(@PathVariable("id") String id, Model model, Authentication authentication) {
        logger.debug("Handling GET /lecture/{}", id);
        try {
            Lecture lecture = lectureService.getLecture(id);
            if (lecture == null) {
                logger.warn("Lecture not found for id: {}", id);
                model.addAttribute("error", "Lecture not found");
                return "error";
            }
            model.addAttribute("lecture", lecture);
            model.addAttribute("notes", lectureNoteService.getNotesByLectureId(id));
            model.addAttribute("comments", commentService.getCommentsByLectureId(id));
            return "lecture";
        } catch (Exception e) {
            logger.error("Error displaying lecture: {}", id, e);
            model.addAttribute("error", "Failed to load lecture: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/lecture/{id}/comment")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    public String addComment(@PathVariable("id") String id,
                             @RequestParam("content") String content,
                             Authentication authentication,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /lecture/{}/comment", id);
        try {
            String username = authentication.getName();
            AppUser user = new AppUser();
            user.setUsername(username);
            commentService.saveComment(id, content, user);
            redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully");
            return "redirect:/lecture/" + id;
        } catch (Exception e) {
            logger.error("Error adding comment to lecture: {}", id, e);
            model.addAttribute("error", "Failed to add comment: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/lecture/{id}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String deleteLecture(@PathVariable("id") String id, Model model, RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /lecture/{}/delete", id);
        try {
            var lecture = lectureService.getLecture(id);
            if (lecture == null) {
                logger.warn("Lecture not found for id: {}", id);
                model.addAttribute("error", " Lecture not found");
                return "error";
            }
            lectureService.deleteLecture(id);
            redirectAttributes.addFlashAttribute("successMessage", "Lecture deleted successfully");
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error deleting lecture: {}", id, e);
            model.addAttribute("error", "Failed to delete lecture: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/lecture/{lectureId}/comment/{commentId}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String deleteComment(@PathVariable("lectureId") String lectureId,
                                @PathVariable("commentId") String commentId,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /lecture/{}/comment/{}/delete", lectureId, commentId);
        try {
            Lecture lecture = lectureService.getLecture(lectureId);
            if (lecture == null) {
                logger.warn("Lecture not found for id: {}", lectureId);
                model.addAttribute("error", "Lecture not found");
                return "error";
            }
            commentService.deleteComment(commentId);
            redirectAttributes.addFlashAttribute("successMessage", "Comment deleted successfully");
            return "redirect:/lecture/" + lectureId;
        } catch (Exception e) {
            logger.error("Error deleting comment: {} for lecture: {}", commentId, lectureId, e);
            model.addAttribute("error", "Failed to delete comment: " + e.getMessage());
            return "error";
        }
    }

    @PostMapping("/lecture/{lectureId}/note/{noteId}/delete")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String deleteNote(@PathVariable("lectureId") String lectureId,
                             @PathVariable("noteId") String noteId,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        logger.debug("Handling POST /lecture/{}/note/{}/delete", lectureId, noteId);
        try {
            Lecture lecture = lectureService.getLecture(lectureId);
            if (lecture == null) {
                logger.warn("Lecture not found for id: {}", lectureId);
                model.addAttribute("error", "Lecture not found");
                return "error";
            }
            lectureNoteService.deleteNote(noteId);
            redirectAttributes.addFlashAttribute("successMessage", "Note deleted successfully");
            return "redirect:/lecture/" + lectureId;
        } catch (Exception e) {
            logger.error("Error deleting note: {} for lecture: {}", noteId, lectureId, e);
            model.addAttribute("error", "Failed to delete note: " + e.getMessage());
            return "error";
        }
    }
}