package com.project.webproject.service;

import com.project.webproject.dao.CommentDao;
import com.project.webproject.model.AppUser;
import com.project.webproject.model.Comment;
import com.project.webproject.model.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private LectureService lectureService;

    @Transactional
    public void saveLectureComment(String lectureId, String content, AppUser author) {
        logger.debug("Saving comment for lecture: {}, author: {}", lectureId, author.getUsername());
        Lecture lecture = lectureService.getLecture(lectureId);
        if (lecture == null) {
            logger.warn("Lecture not found for id: {}", lectureId);
            throw new IllegalArgumentException("Lecture not found");
        }
        Comment comment = new Comment(UUID.randomUUID().toString(), author, content, lecture);
        commentDao.save(comment);
        logger.debug("Comment saved successfully");
    }

    public List<Comment> getCommentsByLectureId(String lectureId) {
        logger.debug("Retrieving comments for lecture: {}", lectureId);
        return commentDao.findByLectureId(lectureId);
    }

    @Transactional
    public void deleteLectureComment(String commentId) {
        logger.debug("Deleting comment: {}", commentId);
        Comment comment = commentDao.findById(commentId);
        if (comment == null) {
            logger.warn("Comment not found for id: {}", commentId);
            throw new IllegalArgumentException("Comment not found");
        }
        commentDao.delete(comment);
        logger.debug("Comment deleted successfully");
    }

    public List<Comment> getAllComments() {
        logger.debug("Retrieving all lecture comments");
        return commentDao.findAllComments();
    }
}