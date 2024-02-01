package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The {@code Answer} class represents an answer entity with its associated properties.
 */
public class Answer extends Entity {

    private String text;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new {@code Answer} instance.
     *
     * @param id                    The unique identifier for the answer.
     * @param text                  The text of the answer.
     * @param createdAt             The creation timestamp of the answer.
     */
    private Answer(UUID id, String text, LocalDateTime createdAt) {
        super(id);
        this.text = text;
        this.createdAt = createdAt;
    }

    /**
     * Provides a builder for creating instances of {@code Answer}.
     *
     * @return An {@code AnswerBuilderId} instance to start building an {@code Answer}.
     */
    public static AnswerBuilderId builder() {
        return id -> text -> createdAt -> () -> new Answer(id, text, createdAt);
    }

    /**
     * Interface for the {@code Answer} builder to set the ID.
     */
    public interface AnswerBuilderId {
        AnswerBuilderText id(UUID id);
    }

    /**
     * Interface for the {@code Answer} builder to set the text.
     */
    public interface AnswerBuilderText {
        AnswerBuilderCreatedAt text(String text);
    }

    /**
     * Interface for the {@code Answer} builder to set the creation date.
     */
    public interface AnswerBuilderCreatedAt {
        AnswerBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Answer} builder.
     */
    public interface AnswerBuilder {
        Answer build();
    }

    /**
     * Gets the text content of the answer.
     *
     * @return The text content of the answer.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text content of the answer.
     *
     * @param text The new text content for the answer.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the creation date of the answer.
     *
     * @return The creation date of the answer.
     */
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
