package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO: зробити валідацію по аналогії з User
 */

public class Test extends Entity {

    private String title;

    private int countOfQuestions;

    private List<Question> questionsList;

    private final LocalDate createdAt;

    private List<Tag> tagList;

    public Test(UUID id, String title, int countOfQuestions, List<Question> questionsList,
            LocalDate createdAt, List<Tag> tagList) {
        super(id);
        this.title = title;
        this.countOfQuestions = countOfQuestions;
        this.questionsList = questionsList;
        this.createdAt = createdAt;
        this.tagList = tagList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(int countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    public List<Question> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<Question> questionsList) {
        this.questionsList = questionsList;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Test test = (Test) o;
        return Objects.equals(title, test.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
