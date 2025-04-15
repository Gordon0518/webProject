package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Poll {
    @Id
    private String id;
    private String question;

    @ManyToOne
    private Course course;

    public Poll() {}
    public Poll(String id, String question, Course course) {
        this.id = id;
        this.question = question;
        this.course = course;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
}