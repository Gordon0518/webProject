package com.project.webproject.service;

import com.project.webproject.dao.LectureNoteDao;
import com.project.webproject.model.LectureNote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureNoteService {

    private static final Logger logger = LoggerFactory.getLogger(LectureNoteService.class);

    @Autowired
    private LectureNoteDao lectureNoteDao;

    public List<LectureNote> getNotesByLectureId(String lectureId) {
        logger.debug("Fetching notes for lecture: {}", lectureId);
        return lectureNoteDao.findByLectureId(lectureId);
    }

    @Transactional
    public void saveNote(LectureNote note) {
        logger.debug("Saving lecture note: {}", note.getId());
        lectureNoteDao.save(note);
    }

    @Transactional
    public void deleteNote(String noteId) {
        logger.debug("Deleting lecture note: {}", noteId);
        LectureNote note = lectureNoteDao.findById(noteId);
        if (note != null) {
            lectureNoteDao.delete(note);
        } else {
            logger.warn("Lecture note not found: {}", noteId);
        }
    }
}