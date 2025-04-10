package com.example.service;

import com.example.model.Vote;
import com.example.model.User;
import com.example.dao.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> findVotesByUser(User user) {
        return voteRepository.findByUser(user);
    }
}