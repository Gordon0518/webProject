package com.project.webproject.service;

import com.project.webproject.dao.PollDao;
import com.project.webproject.model.Poll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollDao pollDao;

    public Poll getPoll(String id) {
        return pollDao.findById(id);
    }

    public List<Poll> getPollsByCourseId(Long courseId) {
        return pollDao.findByCourseId(courseId);
    }
}