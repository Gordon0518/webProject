package com.project.webproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PollComment {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "username")
    private AppUser author;

    private String content;

    @Column(name = "comment_timestamp")
    private LocalDateTime timestamp;

    public PollComment() {
        this.timestamp = LocalDateTime.now();
    }

    public PollComment(String id, Poll poll, AppUser author, String content) {
        this.id = id;
        this.poll = poll;
        this.author = author;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}