package com.project.webproject.service;

import com.project.webproject.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void registerUser(AppUser user) {
        logger.debug("Registering user: {}, password: {}, role: {}", user.getUsername(), user.getPassword(), user.getRole());
        entityManager.persist(user);
        logger.debug("User persisted: {}", user.getUsername());
    }

    public AppUser findByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        AppUser user = entityManager.find(AppUser.class, username);
        if (user != null) {
            logger.debug("Found user: {}, password: {}, role: {}", username, user.getPassword(), user.getRole());
        } else {
            logger.debug("User not found: {}", username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user details for username: {}", username);
        AppUser appUser = findByUsername(username);
        if (appUser == null) {
            logger.warn("User not found during authentication: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        logger.debug("User found: {}, password: {}, role: {}", username, appUser.getPassword(), appUser.getRole());
        UserDetails userDetails = User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().replace("ROLE_", ""))
                .build();
        logger.debug("Created UserDetails: username={}, roles={}", userDetails.getUsername(), userDetails.getAuthorities());
        return userDetails;
    }
}