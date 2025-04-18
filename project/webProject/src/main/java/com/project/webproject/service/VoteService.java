package com.project.webproject.service;

import com.project.webproject.dao.VoteDao;
import com.project.webproject.model.AppUser;
import com.project.webproject.model.PollOption;
import com.project.webproject.model.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VoteService {

    private static final Logger logger = LoggerFactory.getLogger(VoteService.class);

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private PollOptionService pollOptionService;

    @Transactional
    public void castVote(String pollId, String optionId, AppUser voter) {
        logger.debug("Casting vote for poll: {}, option: {}, voter: {}", pollId, optionId, voter.getUsername());
        Vote existingVote = voteDao.findByVoterAndPoll(voter.getUsername(), pollId);
        PollOption newOption = optionId != null ? pollOptionService.getOption(optionId) : null;

        if (newOption == null && optionId != null) {
            logger.warn("Poll option not found for id: {}", optionId);
            return;
        }

        if (existingVote != null) {
            logger.debug("Processing existing vote for voter: {}", voter.getUsername());
            PollOption oldOption = existingVote.getOption();
            if (newOption != null && oldOption != null && oldOption.getId().equals(newOption.getId())) {
                logger.debug("Same option selected, no update needed for voter: {}", voter.getUsername());
                return; // No change needed if the same option is selected
            }
            if (oldOption != null) {
                // Decrease vote count of the old option
                logger.debug("Decreasing vote count for old option: {} from {}", oldOption.getId(), oldOption.getVoteCount());
                oldOption.setVoteCount(Math.max(0, oldOption.getVoteCount() - 1));
                pollOptionService.save(oldOption);
            }
            if (newOption != null) {
                // Update to new option
                logger.debug("Updating vote to new option: {}", newOption.getId());
                existingVote.setOption(newOption);
                newOption.setVoteCount(newOption.getVoteCount() + 1);
                pollOptionService.save(newOption);
                voteDao.update(existingVote);
            } else {
                // Undo vote (remove it)
                logger.debug("Undoing vote for voter: {}, poll: {}", voter.getUsername(), pollId);
                voteDao.delete(existingVote);
            }
        } else if (newOption != null) {
            logger.debug("Creating new vote for voter: {}", voter.getUsername());
            Vote newVote = new Vote(UUID.randomUUID().toString(), newOption, voter, newOption.getPoll());
            newOption.setVoteCount(newOption.getVoteCount() + 1);
            pollOptionService.save(newOption);
            voteDao.save(newVote);
        } else {
            logger.debug("No vote created: no existing vote and no new option selected for voter: {}", voter.getUsername());
        }
        logger.debug("Vote processed successfully");
    }

    public Vote getVoteByVoterAndPoll(String username, String pollId) {
        Vote vote = voteDao.findByVoterAndPoll(username, pollId);
        logger.debug("Retrieved vote for voter: {}, poll: {}, vote: {}", username, pollId, vote != null ? "found" : "null");
        return vote;
    }
}