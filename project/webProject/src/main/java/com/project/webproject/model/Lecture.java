package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Lecture {
    @Id
    private String id;
    private String title;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "lecture")
    private List<LectureNote> notes;

    @OneToMany(mappedBy = "lecture")
    private List<Comment> comments;

    public Lecture() {}
    public Lecture(String id, String title, Course course) {
        this.id = id;
        this.title = title;
        this.course = course;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public List<LectureNote> getNotes() { return notes; }
    public void setNotes(List<LectureNote> notes) { this.notes = notes; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}