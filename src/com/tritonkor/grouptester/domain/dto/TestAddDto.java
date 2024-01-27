package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.Validation;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class TestAddDto extends Entity {

    private String title;
    private int countOfQuestions;
    private Set<Question> questions;
    private Set<Answer> correctAnswers;
    private final LocalDateTime createdAt;

    public TestAddDto(UUID id, String title, int countOfQuestions, Set<Question> questions,
            Set<Answer> correctAnswers, LocalDateTime createdAt) {
        super(id);
        this.title = Validation.validateText(title);
        this.countOfQuestions = countOfQuestions;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.createdAt = Validation.validateDateTime(createdAt);
    }

    public String getTitle() {
        return title;
    }

    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public Set<Answer> getCorrectAnswers() {
        return correctAnswers;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
