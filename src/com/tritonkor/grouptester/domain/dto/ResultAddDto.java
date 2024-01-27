package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.Validation;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResultAddDto extends Entity {

    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    public ResultAddDto(UUID id, String ownerUsername, Grade grade, String testTitle,
            LocalDateTime createdAt) {
        super(id);
        this.ownerUsername = Validation.validateText(ownerUsername);
        this.grade = grade;
        this.testTitle = Validation.validateText(testTitle);
        this.createdAt = Validation.validateDateTime(createdAt);
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}