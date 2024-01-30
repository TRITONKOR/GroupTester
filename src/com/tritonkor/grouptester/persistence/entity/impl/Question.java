package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Question extends Entity {
    
    private String text;
    private final LocalDateTime createdAt;
    private List<Answer> answers;

    private final Answer correctAnswer;

    private Question(UUID id, String text, List<Answer> answers, Answer correctAnswer, LocalDateTime createdAt) {
        super(id);
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.createdAt = createdAt;
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
        QuestionBuilderCorrectAnswer answers(List<Answer> answers);
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(
            List<Answer> answers) {
        this.answers = answers;
    }

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
