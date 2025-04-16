package com.project.webproject.service;

import com.project.webproject.dao.CommentDao;
import com.project.webproject.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public List<Comment> getCommentsByLectureId(String lectureId) {
        return commentDao.findByLectureId(lectureId);
    }
}