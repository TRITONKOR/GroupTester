package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.UUID;

public class Answer extends Entity {

    private String text;
    private final LocalDate createdAt;

    private Answer(UUID id, String text, LocalDate createdAt) {
        super(id);
        this.text = Validation.validateText(text, errors, 100);
        this.createdAt = Validation.validateDate(createdAt, errors);
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

        AnswerBuilder createdAt(LocalDate createdAt);
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
