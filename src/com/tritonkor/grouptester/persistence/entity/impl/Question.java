package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class Question extends Entity {


    private String text;
    private final LocalDateTime createdAt;
    private Set<Answer> answers;

    private final Answer correctAnswer;

    private Question(UUID id, String text, Set<Answer> answers, Answer correctAnswer, LocalDateTime createdAt) {
        super(id);
        this.text = Validation.validateText(text, errors, 200);
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.createdAt = Validation.validateDateTime(createdAt, errors);
    }

    public static QuestionBuilderId builder() {
        return id -> content -> answers -> correctAnswer -> createdAt -> () -> new Question(id, content, answers, correctAnswer, createdAt);
    }

    public interface QuestionBuilderId {
        QuestionBuilderText id(UUID id);
    }

    public interface QuestionBuilderText {
        QuestionBuilderAnswers text(String text);
    }

    public interface QuestionBuilderAnswers {
        QuestionBuilderCorrectAnswer answers(Set<Answer> answers);
    }

    public interface QuestionBuilderCorrectAnswer {
        QuestionBuilderCreatedAt correctAnswer(Answer correctAnswer);
    }

    public interface QuestionBuilderCreatedAt {
        QuestionBuilder createdAt(LocalDateTime createdAt);
    }


    public interface QuestionBuilder {
        Question build();
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

    public Set<Answer> getAnswerList() {
        return answers;
    }

    public void setAnswers(
            Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", answers=" + answers +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
