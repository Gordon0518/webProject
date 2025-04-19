package com.project.webproject.service;

import com.project.webproject.dao.PollCommentDao;
import com.project.webproject.model.AppUser;
import com.project.webproject.model.Poll;
import com.project.webproject.model.PollComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PollCommentService {

    private static final Logger logger = LoggerFactory.getLogger(PollCommentService.class);

    @Autowired
    private PollCommentDao pollCommentDao;

    @Autowired
    private PollService pollService;

    @Transactional
    public void savePollComment(String pollId, String content, AppUser author) {
        logger.debug("Saving comment for poll: {}, author: {}", pollId, author.getUsername());
        Poll poll = pollService.getPoll(pollId);
        if (poll == null) {
            logger.warn("Poll not found for id: {}", pollId);
            throw new IllegalArgumentException("Poll not found");
        }
        PollComment comment = new PollComment(UUID.randomUUID().toString(), poll, author, content);
        pollCommentDao.save(comment);
        logger.debug("Comment saved successfully");
    }

    public List<PollComment> getCommentsByPollId(String pollId) {
        logger.debug("Retrieving comments for poll: {}", pollId);
        return pollCommentDao.findByPollId(pollId);
    }

    @Transactional
    public void deletePollComment(String commentId) {
        logger.debug("Deleting comment: {}", commentId);
        PollComment comment = pollCommentDao.findById(commentId);
        if (comment == null) {
            logger.warn("Comment not found for id: {}", commentId);
            throw new IllegalArgumentException("Comment not found");
        }
        pollCommentDao.delete(comment);
        logger.debug("Comment deleted successfully");
    }

    public List<PollComment> getAllComments() {
        logger.debug("Retrieving all poll comments");
        return pollCommentDao.findAllComments();
    }
}