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
        return entityManager.createQuery("SELECT po FROM PollOption po WHERE po.poll.id = :pollId", PollOption.class)
                .setParameter("pollId", pollId)
                .getResultList();
    }

    public PollOption findById(String id) {
        return entityManager.find(PollOption.class, id);
    }

    public void save(PollOption option) {
        if (option.getId() == null) {
            entityManager.persist(option);
        } else {
            entityManager.merge(option);
        }
    }
}