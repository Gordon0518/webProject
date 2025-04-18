package com.project.webproject.model;

import jakarta.persistence.*;

@Entity
public class PollComment {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "author_username")
    private AppUser author;

    private String content;

    @ManyToOne
    private Poll poll;

    public PollComment() {}
    public PollComment(String id, AppUser author, String content, Poll poll) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.poll = poll;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AppUser getAuthor() { return author; }
    public void setAuthor(AppUser author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}