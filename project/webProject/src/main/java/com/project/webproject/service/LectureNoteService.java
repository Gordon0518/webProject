package com.project.webproject.service;

import com.project.webproject.dao.LectureNoteDao;
import com.project.webproject.model.LectureNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureNoteService {

    @Autowired
    private LectureNoteDao lectureNoteDao;

    public List<LectureNote> getNotesByLectureId(String lectureId) {
        return lectureNoteDao.findByLectureId(lectureId);
    }
}