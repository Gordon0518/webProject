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
                        "SELECT c FROM Comment c WHERE c.lecture.id = :lectureId ORDER BY c.timestamp DESC",
                        Comment.class
                )
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public Comment findById(String commentId) {
        return entityManager.find(Comment.class, commentId);
    }

    public void delete(Comment comment) {
        Comment managedComment = entityManager.contains(comment) ? comment : entityManager.merge(comment);
        entityManager.remove(managedComment);
    }

    public List<Comment> findAllComments() {
        return entityManager.createQuery(
                        "SELECT c FROM Comment c ORDER BY c.timestamp DESC",
                        Comment.class
                )
                .getResultList();
    }
}