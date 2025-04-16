package com.project.webproject.service;

import com.project.webproject.dao.LectureDao;
import com.project.webproject.model.Lecture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    @Autowired
    private LectureDao lectureDao;

    public Lecture getLecture(String id) {
        return lectureDao.findById(id);
    }

    public List<Lecture> getLecturesByCourseId(Long courseId) {
        return lectureDao.findByCourseId(courseId);
    }
}