package com.project.webproject.dao;

import com.project.webproject.model.Lecture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LectureDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Lecture findById(String id) {
        return entityManager.find(Lecture.class, id);
    }

    public List<Lecture> findByCourseId(Long courseId) {
        return entityManager.createQuery("SELECT l FROM Lecture l WHERE l.course.id = :courseId", Lecture.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}