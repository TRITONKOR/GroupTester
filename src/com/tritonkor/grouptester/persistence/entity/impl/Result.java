package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Result extends Entity {

    private User owner;
    private final int mark;
    private final String testTitle;
    private final LocalDateTime createdAt;

    private Result(UUID id, User owner, int mark, String testTitle, LocalDateTime createdAt) {
        super(id);
        this.owner = owner;
        this.mark = mark;
        this.testTitle = testTitle;
        this.createdAt = Validation.validateDateTime(createdAt, errors);
    }

    public static ResultBuilderId builder() {
        return id -> owner -> mark -> testTitle -> createdAt -> () -> new Result(id, owner, mark, testTitle, createdAt);
    }

    public interface ResultBuilderId {
        ResultBuilderOwner id(UUID id);
    }

    public interface ResultBuilderOwner{
        ResultBuilderMark owner(User owner);
    }

    public interface ResultBuilderMark {
        ResultBuilderTestTitle mark(int mark);
    }

    public interface ResultBuilderTestTitle {
        ResultBuilderCreatedAt testTitle(String testTitle);
    }

    public interface ResultBuilderCreatedAt {
        ResultBuilder createdAt(LocalDateTime createdAt);
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

    public String getTestTitle() {
        return testTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Result{" +
                "owner=" + owner +
                ", mark=" + mark +
                ", testTitle=" + testTitle +
                ", createdAt=" + createdAt +
                '}';
    }
}
