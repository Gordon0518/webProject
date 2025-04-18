package com.project.webproject.service;

import com.project.webproject.dao.PollDao;
import com.project.webproject.model.Poll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PollService {

    private static final Logger logger = LoggerFactory.getLogger(PollService.class);

    @Autowired
    private PollDao pollDao;

    public Poll getPoll(String id) {
        logger.debug("Fetching poll: {}", id);
        return pollDao.findByIdWithComments(id);
    }

    public List<Poll> getPollsByCourseId(Long courseId) {
        logger.debug("Fetching polls for course: {}", courseId);
        return pollDao.findByCourseId(courseId);
    }

    @Transactional
    public void savePoll(Poll poll) {
        logger.debug("Saving poll: {}", poll.getId());
        pollDao.save(poll);
    }

    @Transactional
    public void deletePoll(String pollId) {
        logger.debug("Deleting poll: {}", pollId);
        Poll poll = pollDao.findById(pollId);
        if (poll != null) {
            pollDao.delete(poll);
        } else {
            logger.warn("Poll not found: {}", pollId);
        }
    }
}