package com.project.webproject.model;

import jakarta.persistence.*;

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

    public Vote() {}
    public Vote(String id, PollOption option, AppUser voter, Poll poll) {
        this.id = id;
        this.option = option;
        this.voter = voter;
        this.poll = poll;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public PollOption getOption() { return option; }
    public void setOption(PollOption option) { this.option = option; }
    public AppUser getVoter() { return voter; }
    public void setVoter(AppUser voter) { this.voter = voter; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}
