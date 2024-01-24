package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class Result extends Entity {

    private User owner;
    private final int mark;
    private final Test test;
    private final LocalDate createdAt;

    private Result(UUID id, User owner, int mark, Test test, LocalDate createdAt) {
        super(id);
        this.owner = owner;
        this.mark = mark;
        this.test = test;
        this.createdAt = createdAt;
    }

    public static ResultBuilderId builder() {
        return id -> owner -> mark -> test -> createdAt -> () -> new Result(id, owner, mark, test, createdAt);
    }

    public interface ResultBuilderId {
        ResultBuilderOwner id(UUID id);
    }

    public interface ResultBuilderOwner{
        ResultBuilderMark owner(User owner);
    }

    public interface ResultBuilderMark {
        ResultBuilderTest mark(int mark);
    }

    public interface ResultBuilderTest {
        ResultBuilderCreatedAt test(Test test);
    }

    public interface ResultBuilderCreatedAt {
        ResultBuilder createdAt(LocalDate createdAt);
    }

    public interface ResultBuilder {
        Result build();
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
