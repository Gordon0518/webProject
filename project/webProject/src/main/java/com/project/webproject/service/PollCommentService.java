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

import java.util.List;
import java.util.UUID;

@Service
public class PollCommentService {

    private static final Logger logger = LoggerFactory.getLogger(PollCommentService.class);

    @Autowired
    private PollCommentDao pollCommentDao;

    public List<PollComment> getCommentsByPollId(String pollId) {
        logger.debug("Fetching comments for poll: {}", pollId);
        return pollCommentDao.findByPollId(pollId);
    }

    @Transactional
    public void savePollComment(String pollId, String content, AppUser author) {
        logger.debug("Saving poll comment for poll: {}, by user: {}", pollId, author.getUsername());
        PollComment comment = new PollComment(UUID.randomUUID().toString(), author, content, new Poll(pollId, null, null));
        pollCommentDao.save(comment);
        logger.debug("Poll comment saved: {}", comment.getId());
    }

    @Transactional
    public void deletePollComment(String commentId) {
        logger.debug("Deleting poll comment: {}", commentId);
        PollComment comment = pollCommentDao.findById(commentId);
        if (comment != null) {
            pollCommentDao.delete(comment);
            logger.debug("Poll comment deleted: {}", commentId);
        } else {
            logger.warn("Poll comment not found: {}", commentId);
        }
    }
}