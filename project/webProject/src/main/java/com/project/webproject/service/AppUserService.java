package com.project.webproject.service;

import com.project.webproject.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(AppUser user) {
        logger.debug("Registering user: {}, role: {}", user.getUsername(), user.getRole());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
        logger.debug("User persisted: {}", user.getUsername());
    }

    public AppUser findByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        AppUser user = entityManager.find(AppUser.class, username);
        if (user != null) {
            logger.debug("Found user: {}, role: {}", username, user.getRole());
        } else {
            logger.debug("User not found: {}", username);
        }
        return user;
    }

    @Transactional
    public void updateUserProfile(String username, String fullName, String email, String phoneNumber, String password) {
        logger.debug("Updating profile for user: {}", username);
        AppUser user = entityManager.find(AppUser.class, username);
        if (user == null) {
            logger.warn("User not found for profile update: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        entityManager.merge(user);
        logger.debug("User profile updated: {}", username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Attempting to load user details for username: {}", username);
        AppUser appUser = findByUsername(username);
        if (appUser == null) {
            logger.warn("User not found during authentication: {}", username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        logger.debug("User found: {}, role: {}", username, appUser.getRole());
        UserDetails userDetails = User.withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(appUser.getRole().replace("ROLE_", ""))
                .build();
        logger.debug("Created UserDetails: username={}, roles={}", userDetails.getUsername(), userDetails.getAuthorities());
        return userDetails;
    }

    @Transactional
    public void addUser(String username, String password, String role, String fullName, String email, String phoneNumber) {
        logger.debug("Adding user: username={}, role={}", username, role);
        if (findByUsername(username) != null) {
            logger.warn("User already exists: {}", username);
            throw new IllegalArgumentException("Username already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        entityManager.persist(user);
        logger.debug("User added: {}", username);
    }

    @Transactional
    public void updateUser(String username, String password, String role, String fullName, String email, String phoneNumber) {
        logger.debug("Updating user: username={}", username);
        AppUser user = findByUsername(username);
        if (user == null) {
            logger.warn("User not found for update: {}", username);
            throw new IllegalArgumentException("User not found");
        }
        if (password != null && !password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        if (role != null && !role.isEmpty()) {
            user.setRole(role);
        }
        if (fullName != null) {
            user.setFullName(fullName);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        entityManager.merge(user);
        logger.debug("User updated: {}", username);
    }

    @Transactional
    public void deleteUser(String username) {
        logger.debug("Deleting user: username={}", username);
        AppUser user = findByUsername(username);
        if (user == null) {
            logger.warn("User not found for deletion: {}", username);
            throw new IllegalArgumentException("User not found");
        }
        entityManager.remove(user);
        logger.debug("User deleted: {}", username);
    }

    public List<AppUser> getAllUsers() {
        logger.debug("Retrieving all users");
        return entityManager.createQuery("SELECT u FROM AppUser u", AppUser.class)
                .getResultList();
    }
}