package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class Question extends Entity {


    private String content;
    private final LocalDate createdAt;
    private List<Answer> answerList;

    private final Answer correctAnswer;

    public Question(UUID id, String content, LocalDate createdAt, List<Answer> answerList, Answer correctAnswer) {
        super(id);
        this.content = Validation.validateText(content, errors, 120);
        this.createdAt = Validation.validateDate(createdAt, errors);
        this.answerList = new ArrayList<>();
        this.correctAnswer = correctAnswer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(
            List<Answer> answerList) {
        this.answerList = answerList;
    }

    @Override
    public String toString() {
        return "Question{" +
                "content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
