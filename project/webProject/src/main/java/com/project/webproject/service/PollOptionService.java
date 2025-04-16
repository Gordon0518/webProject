package com.project.webproject.service;

import com.project.webproject.dao.PollOptionDao;
import com.project.webproject.model.PollOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollOptionService {

    @Autowired
    private PollOptionDao pollOptionDao;

    public List<PollOption> getOptionsByPollId(String pollId) {
        return pollOptionDao.findByPollId(pollId);
    }
}