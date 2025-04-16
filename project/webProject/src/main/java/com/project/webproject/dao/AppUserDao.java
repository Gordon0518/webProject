package com.project.webproject.dao;

import com.project.webproject.model.AppUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class AppUserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void save(AppUser user) {
        entityManager.persist(user);
    }

    public AppUser findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM AppUser u WHERE u.username = :username", AppUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}