package com.project.webproject.dao;

import com.project.webproject.model.Poll;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PollDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Poll findById(String id) {
        return entityManager.find(Poll.class, id);
    }

    public List<Poll> findByCourseId(Long courseId) {
        return entityManager.createQuery("SELECT p FROM Poll p WHERE p.course.id = :courseId", Poll.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }
}