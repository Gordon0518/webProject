package com.project.webproject.dao;

import com.project.webproject.model.PollOption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PollOptionDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<PollOption> findByPollId(String pollId) {
        return entityManager.createQuery("SELECT o FROM PollOption o WHERE o.poll.id = :pollId", PollOption.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }
}