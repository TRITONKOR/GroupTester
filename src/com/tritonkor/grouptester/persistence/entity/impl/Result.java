package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.domain.Validation;
import java.time.LocalDateTime;
import java.util.UUID;

public class Result extends Entity {

    private final String ownerUsername;
    private final int mark;
    private final String testTitle;
    private final LocalDateTime createdAt;

    private Result(UUID id, String ownerUsername, int mark, String testTitle, LocalDateTime createdAt) {
        super(id);
        this.ownerUsername = Validation.validateText(ownerUsername, errors, 24);
        this.mark = mark;
        this.testTitle = testTitle;
        this.createdAt = Validation.validateDateTime(createdAt, errors);
    }

    public static ResultBuilderId builder() {
        return id -> ownerUsername -> mark -> testTitle -> createdAt -> () -> new Result(id, ownerUsername, mark, testTitle, createdAt);
    }

    public interface ResultBuilderId {
        ResultBuilderOwnerUsername id(UUID id);
    }

    public interface ResultBuilderOwnerUsername{
        ResultBuilderMark ownerUsername(String ownerUsername);
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

    public String getOwnerUsername() {
        return ownerUsername;
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
                "ownerUsername=" + ownerUsername +
                ", mark=" + mark +
                ", testTitle=" + testTitle +
                ", createdAt=" + createdAt +
                '}';
    }
}
