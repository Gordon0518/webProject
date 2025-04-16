package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PollComment {
    @Id
    private String id;
    private String author;
    private String content;

    @ManyToOne
    private Poll poll;

    public PollComment() {}
    public PollComment(String id, String author, String content, Poll poll) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.poll = poll;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}