package com.example.dao;

import com.example.model.Course;
import com.example.model.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, Long> {
    List<CourseMaterial> findByCourse(Course course);
}