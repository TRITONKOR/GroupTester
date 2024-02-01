package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Result} class represents a result of a test for a specific user.
 */
public class Result extends Entity {

    private String resultTitle;
    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new {@code Result} instance.
     *
     * @param id                            The unique identifier for the result.
     * @param resultTitle                   The title of the result.
     * @param ownerUsername                 The username of the owner.
     * @param testTitle                     The title of the test.
     * @param grade                         The user grade.
     * @param createdAt                     The creation timestamp of the result.
     */
    private Result(UUID id, String resultTitle, String ownerUsername, String testTitle, Grade grade, LocalDateTime createdAt) {
        super(id);
        this.resultTitle = resultTitle;
        this.ownerUsername = ownerUsername;
        this.testTitle = testTitle;
        this.grade = grade;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code ResultBuilderId} instance to start building a {@code Result}.
     *
     * @return A {@code ResultBuilderId} instance.
     */
    public static ResultBuilderId builder() {
        return id -> resultTitle -> ownerUsername -> testTitle -> grade -> createdAt -> () -> new Result(id, resultTitle, ownerUsername, testTitle, grade, createdAt);
    }

    /**
     * Interface for the {@code Result} builder to set the ID.
     */
    public interface ResultBuilderId {
        ResultBuilderResultTitle id(UUID id);
    }

    /**
     * Interface for the {@code Result} builder to set the title.
     */
    public interface ResultBuilderResultTitle{
        ResultBuilderOwnerUsername resultTitle(String resultTitle);
    }

    /**
     * Interface for the {@code Result} builder to set the owner username.
     */
    public interface ResultBuilderOwnerUsername{
        ResultBuilderTestTitle ownerUsername(String ownerUsername);
    }

    /**
     * Interface for the {@code Result} builder to set the test title.
     */
    public interface ResultBuilderTestTitle {
        ResultBuilderGrade testTitle(String testTitle);
    }

    /**
     * Interface for the {@code Result} builder to set the grade.
     */
    public interface ResultBuilderGrade {
        ResultBuilderCreatedAt grade(Grade grade);
    }

    /**
     * Interface for the {@code Result} builder to set the creation date.
     */
    public interface ResultBuilderCreatedAt {
        ResultBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Result} builder.
     */
    public interface ResultBuilder {
        Result build();
    }

    /**
     * Sets the title of the result.
     *
     * @param resultTitle The new result title.
     */
    public void setResultTitle(String resultTitle) {
        this.resultTitle = resultTitle;
    }

    /**
     * Gets the title of the result.
     *
     * @return The result title.
     */
    public String getResultTitle() {
        return resultTitle;
    }

    /**
     * Gets the username of the user who owns the result.
     *
     * @return The owner's username.
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Gets the grade associated with the result.
     *
     * @return The grade.
     */
    public Grade getMark() {
        return grade;
    }

    /**
     * Gets the title of the test associated with the result.
     *
     * @return The test title.
     */
    public String getTestTitle() {
        return testTitle;
    }

    /**
     * Gets the creation timestamp of the result.
     *
     * @return The creation timestamp.
     */
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
