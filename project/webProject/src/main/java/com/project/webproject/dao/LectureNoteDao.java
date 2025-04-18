package com.project.webproject.dao;

import com.project.webproject.model.LectureNote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LectureNoteDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<LectureNote> findByLectureId(String lectureId) {
        return entityManager.createQuery("SELECT n FROM LectureNote n WHERE n.lecture.id = :lectureId", LectureNote.class)
                .setParameter("lectureId", lectureId)
                .getResultList();
    }

    public void save(LectureNote note) {
        entityManager.persist(note);
    }

    public void delete(LectureNote note) {
        LectureNote managedNote = entityManager.contains(note) ? note : entityManager.merge(note);
        entityManager.remove(managedNote);
    }

    public LectureNote findById(String id) {
        return entityManager.find(LectureNote.class, id);
    }
}