package com.project.webproject.service;

import com.project.webproject.dao.CourseDao;
import com.project.webproject.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseDao courseDao;

    public Course getCourse(Long id) {
        return courseDao.findById(id);
    }
}