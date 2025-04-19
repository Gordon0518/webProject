package com.project.webproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "author_username")
    private AppUser author;

    private String content;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "comment_timestamp")
    private LocalDateTime timestamp;

    public Comment() {
        this.timestamp = LocalDateTime.now();
    }

    public Comment(String id, AppUser author, String content, Lecture lecture) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.lecture = lecture;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Lecture getLecture() { return lecture; }
    public void setLecture(Lecture lecture) { this.lecture = lecture; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}