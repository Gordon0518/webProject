package com.project.webproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PollOption {
    @Id
    private String id;
    private String optionText;
    private int voteCount;

    @ManyToOne
    private Poll poll;

    public PollOption() {}
    public PollOption(String id, String optionText, int voteCount, Poll poll) {
        this.id = id;
        this.optionText = optionText;
        this.voteCount = voteCount;
        this.poll = poll;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOptionText() { return optionText; }
    public void setOptionText(String optionText) { this.optionText = optionText; }
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}