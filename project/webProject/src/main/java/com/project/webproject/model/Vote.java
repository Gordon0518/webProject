package com.project.webproject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Vote {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private PollOption option;

    @ManyToOne
    @JoinColumn(name = "username")
    private AppUser voter;

    @ManyToOne
    private Poll poll;

    @Column(name = "vote_timestamp")
    private LocalDateTime voteTimestamp;

    public Vote() {
        this.voteTimestamp = LocalDateTime.now();
    }

    public Vote(String id, PollOption option, AppUser voter, Poll poll) {
        this.id = id;
        this.option = option;
        this.voter = voter;
        this.poll = poll;
        this.voteTimestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public PollOption getOption() { return option; }
    public void setOption(PollOption option) { this.option = option; }
    public AppUser getVoter() { return voter; }
    public void setVoter(AppUser voter) { this.voter = voter; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
    public LocalDateTime getVoteTimestamp() { return voteTimestamp; }
    public void setVoteTimestamp(LocalDateTime voteTimestamp) { this.voteTimestamp = voteTimestamp; }
}