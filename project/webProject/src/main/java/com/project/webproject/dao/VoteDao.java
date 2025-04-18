package com.project.webproject.dao;

import com.project.webproject.model.Vote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class VoteDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(Vote vote) {
        entityManager.persist(vote);
    }

    public Vote findByVoterAndPoll(String username, String pollId) {
        try {
            return entityManager.createQuery("SELECT v FROM Vote v WHERE v.voter.username = :username AND v.poll.id = :pollId", Vote.class)
                    .setParameter("username", username)
                    .setParameter("pollId", pollId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Vote vote) {
        entityManager.merge(vote);
    }

    public void delete(Vote vote) {
        Vote managedVote = entityManager.contains(vote) ? vote : entityManager.merge(vote);
        entityManager.remove(managedVote);
    }
}