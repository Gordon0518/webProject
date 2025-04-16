package com.project.webproject.dao;

import com.project.webproject.model.Comment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Comment> findByLectureId(String lectureId) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.lecture.id = :lectureId", Comment.class)
                .setParameter("lectureId", lectureId)
                .getResultList();
    }
}