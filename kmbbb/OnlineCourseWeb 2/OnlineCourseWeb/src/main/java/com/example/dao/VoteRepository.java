package com.example.dao;

import com.example.model.CourseMaterial;
import com.example.model.User;
import com.example.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByUser(User user);
}