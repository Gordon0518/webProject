package com.example.dao;

import com.example.model.Comment;
import com.example.model.CourseMaterial;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUser(User user);
    List<Comment> findByMaterial(CourseMaterial material);
}