package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    private String id;
    private String author;
    private String content;

    @ManyToOne
    private Lecture lecture;

    public Comment() {}
    public Comment(String id, String author, String content, Lecture lecture) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.lecture = lecture;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }
}