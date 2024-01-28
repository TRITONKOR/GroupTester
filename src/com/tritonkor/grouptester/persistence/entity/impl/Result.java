package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Result extends Entity {

    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    private Result(UUID id, String ownerUsername, Grade grade, String testTitle, LocalDateTime createdAt) {
        super(id);
        this.ownerUsername = ownerUsername;
        this.grade = grade;
        this.testTitle = testTitle;
        this.createdAt = createdAt;
    }

    public static ResultBuilderId builder() {
        return id -> ownerUsername -> grade -> testTitle -> createdAt -> () -> new Result(id, ownerUsername, grade, testTitle, createdAt);
    }

    public interface ResultBuilderId {
        ResultBuilderOwnerUsername id(UUID id);
    }

    public interface ResultBuilderOwnerUsername{
        ResultBuilderGrade ownerUsername(String ownerUsername);
    }

    public interface ResultBuilderGrade {
        ResultBuilderTestTitle grade(Grade grade);
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

    public Grade getMark() {
        return grade;
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
                "ownerUsername=" + ownerUsername + grade +
                ", testTitle=" + testTitle +
                ", createdAt=" + createdAt +
                '}';
    }
}
