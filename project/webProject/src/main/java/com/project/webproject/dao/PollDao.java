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

    public Poll findByIdWithComments(String id) {
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Poll p LEFT JOIN FETCH p.comments WHERE p.id = :id", Poll.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Poll> findByCourseId(Long courseId) {
        return entityManager.createQuery(
                        "SELECT p FROM Poll p WHERE p.course.id = :courseId", Poll.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public void save(Poll poll) {
        if (poll.getId() == null) {
            entityManager.persist(poll);
        } else {
            entityManager.merge(poll);
        }
    }

    public void delete(Poll poll) {
        Poll managedPoll = entityManager.contains(poll) ? poll : entityManager.merge(poll);
        entityManager.remove(managedPoll);
    }
}