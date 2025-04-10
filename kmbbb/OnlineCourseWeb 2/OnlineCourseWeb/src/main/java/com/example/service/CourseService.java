package com.example.service;

import com.example.model.Course;
import com.example.model.CourseMaterial;
import com.example.model.User;
import com.example.dao.CourseMaterialRepository;
import com.example.dao.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseMaterialRepository materialRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findCoursesByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public CourseMaterial saveMaterial(CourseMaterial material) {
        return materialRepository.save(material);
    }

    public List<CourseMaterial> findMaterialsByCourse(Course course) {
        return materialRepository.findByCourse(course);
    }
}