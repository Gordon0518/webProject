package com.project.webproject.service;

import com.project.webproject.dao.PollOptionDao;
import com.project.webproject.model.PollOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PollOptionService {

    private static final Logger logger = LoggerFactory.getLogger(PollOptionService.class);

    @Autowired
    private PollOptionDao pollOptionDao;

    public List<PollOption> getOptionsByPollId(String pollId) {
        logger.debug("Fetching options for poll: {}", pollId);
        return pollOptionDao.findByPollId(pollId);
    }

    public PollOption getOption(String id) {
        logger.debug("Fetching option: {}", id);
        return pollOptionDao.findById(id);
    }

    @Transactional
    public void save(PollOption option) {
        logger.debug("Saving poll option: {}, voteCount: {}", option.getId(), option.getVoteCount());
        pollOptionDao.save(option);
    }

    @Transactional
    public void saveAll(List<PollOption> options) {
        logger.debug("Saving {} poll options", options.size());
        for (PollOption option : options) {
            pollOptionDao.save(option);
        }
    }
}