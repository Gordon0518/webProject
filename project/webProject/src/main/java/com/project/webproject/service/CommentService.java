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

    public List<Comment> getCommentsByLectureId(String lectureId) {
        logger.debug("Fetching comments for lecture: {}", lectureId);
        return commentDao.findByLectureId(lectureId);
    }

    @Transactional
    public void saveComment(String lectureId, String content, AppUser author) {
        logger.debug("Saving comment for lecture: {}, by user: {}", lectureId, author.getUsername());
        Comment comment = new Comment(UUID.randomUUID().toString(), author, content, new Lecture(lectureId, null, null));
        commentDao.save(comment);
        logger.debug("Comment saved: {}", comment.getId());
    }

    @Transactional
    public void deleteComment(String commentId) {
        logger.debug("Deleting comment: {}", commentId);
        Comment comment = commentDao.findById(commentId);
        if (comment != null) {
            commentDao.delete(comment);
            logger.debug("Comment deleted: {}", commentId);
        } else {
            logger.warn("Comment not found: {}", commentId);
        }
    }
}