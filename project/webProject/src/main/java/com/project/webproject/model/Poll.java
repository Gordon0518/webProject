package com.project.webproject.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Poll {
    @Id
    private String id;
    private String question;

    @ManyToOne
    private Course course;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PollOption> options;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PollComment> comments;

    public Poll() {}
    public Poll(String id, String question, Course course) {
        this.id = id;
        this.question = question;
        this.course = course;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }
    public List<PollOption> getOptions() { return options; }
    public void setOptions(List<PollOption> options) { this.options = options; }
    public List<PollComment> getComments() { return comments; }
    public void setComments(List<PollComment> comments) { this.comments = comments; }
}