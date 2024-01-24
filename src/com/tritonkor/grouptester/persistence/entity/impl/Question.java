package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;

public class Question extends Entity {


    private String text;
    private final LocalDate createdAt;
    private List<Answer> answerList;

    private final Answer correctAnswer;

    private Question(UUID id, String text, LocalDate createdAt, List<Answer> answerList, Answer correctAnswer) {
        super(id);
        this.text = Validation.validateText(text, errors, 200);
        this.createdAt = Validation.validateDate(createdAt, errors);
        this.answerList = new ArrayList<>();
        this.correctAnswer = correctAnswer;
    }

    public static QuestionBuilderId builder() {
        return id -> content -> createdAt -> answerList -> correctAnswer -> () -> new Question(id, content, createdAt, answerList, correctAnswer);
    }

    public interface QuestionBuilderId {
        QuestionBuilderText id(UUID id);
    }

    public interface QuestionBuilderText {
        QuestionBuilderCreatedAt text(String text);
    }

    public interface QuestionBuilderCreatedAt {
        QuestionBuilderAnswerList createdAt(LocalDate createdAt);
    }

    public interface QuestionBuilderAnswerList {
        QuestionBuilderCorrectAnswer answerList(List<Answer> answerList);
    }

    public interface QuestionBuilderCorrectAnswer {
        QuestionBuilder correctAnswer(Answer correctAnswer);
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
                "text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", answerList=" + answerList +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
