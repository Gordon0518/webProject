package com.project.webproject.service;

import com.project.webproject.dao.LectureDao;
import com.project.webproject.model.Lecture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LectureService {

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    @Autowired
    private LectureDao lectureDao;

    public Lecture getLecture(String id) {
        logger.debug("Fetching lecture: {}", id);
        return lectureDao.findById(id);
    }

    public List<Lecture> getLecturesByCourseId(Long courseId) {
        logger.debug("Fetching lectures for course: {}", courseId);
        return lectureDao.findByCourseId(courseId);
    }

    @Transactional
    public void saveLecture(Lecture lecture) {
        logger.debug("Saving lecture: {}", lecture.getId());
        lectureDao.save(lecture);
    }

    @Transactional
    public void deleteLecture(String lectureId) {
        logger.debug("Deleting lecture: {}", lectureId);
        Lecture lecture = lectureDao.findById(lectureId);
        if (lecture != null) {
            lectureDao.delete(lecture);
        } else {
            logger.warn("Lecture not found: {}", lectureId);
        }
    }
}