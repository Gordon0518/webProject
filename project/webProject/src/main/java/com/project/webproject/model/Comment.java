package com.project.webproject.model;

import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "author_username")
    private AppUser author;

    private String content;

    @ManyToOne
    private Lecture lecture;

    public Comment() {}
    public Comment(String id, AppUser author, String content, Lecture lecture) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.lecture = lecture;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }
}