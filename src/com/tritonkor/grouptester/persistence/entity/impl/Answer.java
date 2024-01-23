package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class Answer extends Entity {

    private String text;
    private final LocalDate createdAt;

    public Answer(UUID id, String text, LocalDate createdAt) {
        super(id);
        this.text = text;
        this.createdAt = createdAt;
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
