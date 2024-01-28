package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Answer extends Entity {

    private String text;
    private final LocalDateTime createdAt;

    private Answer(UUID id, String text, LocalDateTime createdAt) {
        super(id);
        this.text = text;
        this.createdAt = createdAt;
    }

    public static AnswerBuilderId builder() {
        return id -> text -> createdAt -> () -> new Answer(id, text, createdAt);
    }

    public interface AnswerBuilderId {

        AnswerBuilderText id(UUID id);
    }

    public interface AnswerBuilderText {

        AnswerBuilderCreatedAt text(String text);
    }

    public interface AnswerBuilderCreatedAt {

        AnswerBuilder createdAt(LocalDateTime createdAt);
    }

    public interface AnswerBuilder {

        Answer build();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
