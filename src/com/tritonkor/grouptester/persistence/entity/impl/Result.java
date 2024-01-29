package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Result extends Entity {

    private String resultTitle;
    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    private Result(UUID id, String resultTitle, String ownerUsername, String testTitle, Grade grade, LocalDateTime createdAt) {
        super(id);
        this.resultTitle = resultTitle;
        this.ownerUsername = ownerUsername;
        this.testTitle = testTitle;
        this.grade = grade;
        this.createdAt = createdAt;
    }

    public static ResultBuilderId builder() {
        return id -> resultTitle -> ownerUsername -> testTitle -> grade -> createdAt -> () -> new Result(id, resultTitle, ownerUsername, testTitle, grade, createdAt);
    }

    public interface ResultBuilderId {
        ResultBuilderResultTitle id(UUID id);
    }

    public interface ResultBuilderResultTitle{
        ResultBuilderOwnerUsername resultTitle(String resultTitle);
    }

    public interface ResultBuilderOwnerUsername{
        ResultBuilderTestTitle ownerUsername(String ownerUsername);
    }

    public interface ResultBuilderTestTitle {
        ResultBuilderGrade testTitle(String testTitle);
    }

    public interface ResultBuilderGrade {
        ResultBuilderCreatedAt grade(Grade grade);
    }

    public interface ResultBuilderCreatedAt {
        ResultBuilder createdAt(LocalDateTime createdAt);
    }

    public interface ResultBuilder {
        Result build();
    }

    public void setResultTitle(String resultName) {
        this.resultTitle = resultTitle;
    }

    public String getResultTitle() {
        return resultTitle;
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
                "resultTitle='" + resultTitle + '\'' +
                ", ownerUsername='" + ownerUsername + '\'' +
                ", grade=" + grade +
                ", testTitle='" + testTitle + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
