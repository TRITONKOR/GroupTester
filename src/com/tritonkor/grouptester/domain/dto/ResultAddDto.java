package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The ResultAddDto class represents a data transfer object for adding a result. It extends the
 * Entity class, providing identification through a UUID.
 */
public class ResultAddDto extends Entity {

    private String resultTitle;
    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new ResultAddDto with the specified parameters.
     *
     * @param id            the unique identifier for the result.
     * @param resultTitle   the title of the result.
     * @param ownerUsername the username of the owner/user associated with the result.
     * @param grade         the grade associated with the result.
     * @param testTitle     the title of the test associated with the result.
     * @param createdAt     the date and time when the result was created.
     */
    public ResultAddDto(UUID id, String resultTitle, String ownerUsername, Grade grade,
            String testTitle,
            LocalDateTime createdAt) {
        super(id);
        this.resultTitle = ValidationUtil.validateTitle(resultTitle);
        this.ownerUsername = ValidationUtil.validateName(ownerUsername);
        this.grade = grade;
        this.testTitle = ValidationUtil.validateTitle(testTitle);
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    /**
     * Gets the title of the result.
     *
     * @return the title of the result.
     */
    public String getResultTitle() {
        return resultTitle;
    }

    /**
     * Gets the username of the owner/user associated with the result.
     *
     * @return the username of the owner/user associated with the result.
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Gets the grade associated with the result.
     *
     * @return the grade associated with the result.
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Gets the title of the test associated with the result.
     *
     * @return the title of the test associated with the result.
     */
    public String getTestTitle() {
        return testTitle;
    }

    /**
     * Gets the date and time when the result was created.
     *
     * @return the date and time when the result was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
