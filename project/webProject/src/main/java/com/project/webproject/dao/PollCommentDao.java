package com.project.webproject.dao;

import com.project.webproject.model.PollComment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PollCommentDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(PollComment comment) {
        entityManager.persist(comment);
    }

    public List<PollComment> findByPollId(String pollId) {
        return entityManager.createQuery(
                        "SELECT c FROM PollComment c WHERE c.poll.id = :pollId ORDER BY c.timestamp DESC",
                        PollComment.class
                )
                .setParameter("pollId", pollId)
                .getResultList();
    }

    public void delete(PollComment comment) {
        PollComment managedComment = entityManager.contains(comment) ? comment : entityManager.merge(comment);
        entityManager.remove(managedComment);
    }

    public PollComment findById(String commentId) {
        return entityManager.find(PollComment.class, commentId);
    }

    public List<PollComment> findAllComments() {
        return entityManager.createQuery(
                        "SELECT c FROM PollComment c ORDER BY c.timestamp DESC",
                        PollComment.class
                )
                .getResultList();
    }
}