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

    private Question(UUID id, String text, List<Answer> answerList, Answer correctAnswer, LocalDate createdAt) {
        super(id);
        this.text = Validation.validateText(text, errors, 200);
        this.answerList = answerList;
        this.correctAnswer = correctAnswer;
        this.createdAt = Validation.validateDate(createdAt, errors);
    }

    public static QuestionBuilderId builder() {
        return id -> content -> answerList -> correctAnswer -> createdAt -> () -> new Question(id, content, answerList, correctAnswer, createdAt);
    }

    public interface QuestionBuilderId {
        QuestionBuilderText id(UUID id);
    }

    public interface QuestionBuilderText {
        QuestionBuilderAnswerList text(String text);
    }

    public interface QuestionBuilderAnswerList {
        QuestionBuilderCorrectAnswer answerList(List<Answer> answerList);
    }

    public interface QuestionBuilderCorrectAnswer {
        QuestionBuilderCreatedAt correctAnswer(Answer correctAnswer);
    }

    public interface QuestionBuilderCreatedAt {
        QuestionBuilder createdAt(LocalDate createdAt);
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
