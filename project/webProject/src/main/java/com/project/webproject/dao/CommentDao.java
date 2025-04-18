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

    public void save(Comment comment) {
        entityManager.persist(comment);
    }

    public List<Comment> findByLectureId(String lectureId) {
        return entityManager.createQuery(
                        "SELECT c FROM Comment c LEFT JOIN FETCH c.author WHERE c.lecture.id = :lectureId", Comment.class)
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public Comment findById(String id) {
        return entityManager.find(Comment.class, id);
    }

    public void delete(Comment comment) {
        Comment managedComment = entityManager.contains(comment) ? comment : entityManager.merge(comment);
        entityManager.remove(managedComment);
    }
}