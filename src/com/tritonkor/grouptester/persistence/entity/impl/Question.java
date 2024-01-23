package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

public class Question extends Entity {

    private String content;
    private final LocalDate createdAt;
    private List<Answer> answerList;

    public Question(UUID id, String content, LocalDate createdAt, List<Answer> answerList) {
        super(id);
        this.content = content;
        this.createdAt = createdAt;
        this.answerList = answerList;
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
