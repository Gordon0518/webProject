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

    public List<PollComment> findByPollId(String pollId) {
        return entityManager.createQuery("SELECT c FROM PollComment c WHERE c.poll.id = :pollId", PollComment.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }
}