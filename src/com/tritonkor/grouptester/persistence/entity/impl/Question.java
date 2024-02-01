package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The {@code Question} class represents a question with its associated properties.
 */
public class Question extends Entity {
    
    private String text;
    private final LocalDateTime createdAt;
    private List<Answer> answers;

    private final Answer correctAnswer;

    /**
     * Constructs a new {@code Question} instance.
     *
     * @param id                    The unique identifier for the question.
     * @param text                  The text of the question.
     * @param answers               The set of the answers.
     * @param correctAnswer         The correct answer in the question.
     * @param createdAt             The creation timestamp of the question.
     */
    private Question(UUID id, String text, List<Answer> answers, Answer correctAnswer, LocalDateTime createdAt) {
        super(id);
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code QuestionBuilderId} instance to start building a {@code Question}.
     *
     * @return A {@code QuestionBuilderId} instance.
     */
    public static QuestionBuilderId builder() {
        return id -> content -> answers -> correctAnswer -> createdAt -> () -> new Question(id, content, answers, correctAnswer, createdAt);
    }

    /**
     * Interface for the {@code Question} builder to set the ID.
     */
    public interface QuestionBuilderId {
        QuestionBuilderText id(UUID id);
    }

    /**
     * Interface for the {@code Question} builder to set the text.
     */
    public interface QuestionBuilderText {
        QuestionBuilderAnswers text(String text);
    }

    /**
     * Interface for the {@code Question} builder to set the answers.
     */
    public interface QuestionBuilderAnswers {
        QuestionBuilderCorrectAnswer answers(List<Answer> answers);
    }

    /**
     * Interface for the {@code Question} builder to set the correct answer.
     */
    public interface QuestionBuilderCorrectAnswer {
        QuestionBuilderCreatedAt correctAnswer(Answer correctAnswer);
    }

    /**
     * Interface for the {@code Question} builder to set the creation date.
     */
    public interface QuestionBuilderCreatedAt {
        QuestionBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Question} builder.
     */
    public interface QuestionBuilder {
        Question build();
    }

    /**
     * Gets the text of the question.
     *
     * @return The question text.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the question.
     *
     * @param text The new question text.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets the creation timestamp of the question.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the list of answers associated with the question.
     *
     * @return The list of answers.
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * Sets the list of answers associated with the question.
     *
     * @param answers The new list of answers.
     */
    public void setAnswers(
            List<Answer> answers) {
        this.answers = answers;
    }

    /**
     * Gets the correct answer for the question.
     *
     * @return The correct answer.
     */
    public Answer getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
