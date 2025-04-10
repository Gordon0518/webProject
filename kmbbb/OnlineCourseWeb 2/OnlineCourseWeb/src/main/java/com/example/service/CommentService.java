package com.example.service;

import com.example.model.Comment;
import com.example.model.CourseMaterial;
import com.example.model.User;
import com.example.dao.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> findCommentsByUser(User user) {
        return commentRepository.findByUser(user);
    }

    public List<Comment> findCommentsByMaterial(CourseMaterial material) {
        return commentRepository.findByMaterial(material);
    }
}