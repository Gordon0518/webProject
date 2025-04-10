package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private CourseMaterial material;
    private int voteValue; // 1 or -1

    public Vote() {}
    public Vote(User user, CourseMaterial material, int voteValue) {
        this.user = user;
        this.material = material;
        this.voteValue = voteValue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public CourseMaterial getMaterial() { return material; }
    public void setMaterial(CourseMaterial material) { this.material = material; }
    public int getVoteValue() { return voteValue; }
    public void setVoteValue(int voteValue) { this.voteValue = voteValue; }
}