package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResultAddDto extends Entity {

    private String resultTitle;
    private final String ownerUsername;
    private final Grade grade;
    private final String testTitle;
    private final LocalDateTime createdAt;

    public ResultAddDto(UUID id, String resultTitle, String ownerUsername, Grade grade, String testTitle,
            LocalDateTime createdAt) {
        super(id);
        this.resultTitle = ValidationUtil.validateTitle(resultTitle);
        this.ownerUsername = ValidationUtil.validateName(ownerUsername);
        this.grade = grade;
        this.testTitle = ValidationUtil.validateTitle(testTitle);
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    public String getResultTitle() {
        return resultTitle;
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
