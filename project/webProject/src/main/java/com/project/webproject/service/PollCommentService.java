package com.project.webproject.service;

import com.project.webproject.dao.PollCommentDao;
import com.project.webproject.model.PollComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollCommentService {

    @Autowired
    private PollCommentDao pollCommentDao;

    public List<PollComment> getCommentsByPollId(String pollId) {
        return pollCommentDao.findByPollId(pollId);
    }
}