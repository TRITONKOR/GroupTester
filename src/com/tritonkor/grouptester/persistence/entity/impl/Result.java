package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class Result extends Entity {

    private User owner;
    private final int mark;
    private final Test test;
    private final LocalDate createdAt;

    public Result(UUID id, User owner, int mark, Test test, LocalDate createdAt) {
        super(id);
        this.owner = owner;
        this.mark = mark;
        this.test = test;
        this.createdAt = createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getMark() {
        return mark;
    }

    public Test getTest() {
        return test;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Result{" +
                "owner=" + owner +
                ", mark=" + mark +
                ", test=" + test +
                ", createdAt=" + createdAt +
                '}';
    }
}
