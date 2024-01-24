package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;

import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Test extends Entity implements Comparable<Test> {

    private String title;

    private int countOfQuestions;

    private List<Question> questionsList;

    private final LocalDate createdAt;

    private Test(UUID id, String title, int countOfQuestions, List<Question> questionsList,
            LocalDate createdAt) {
        super(id);
        this.title = Validation.validateText(title, errors, 100);
        this.countOfQuestions = countOfQuestions;
        this.questionsList = questionsList;
        this.createdAt = Validation.validateDate(createdAt, errors);
    }

    public static TestBuilderId builder() {
        return id -> title -> countOfQuestions -> questionsList -> createdAt -> () -> new Test(id,
                title, countOfQuestions, questionsList, createdAt);
    }

    public interface TestBuilderId {

        TestBuilderTitle id(UUID id);
    }

    public interface TestBuilderTitle {

        TestBuilderCountOfQuestions title(String title);
    }

    public interface TestBuilderCountOfQuestions {

        TestBuilderQuestionsList countOfQuestionsle(int countOfQuestions);
    }

    public interface TestBuilderQuestionsList {

        TestBuilderCreatedAt questionsList(List<Question> questionList);
    }

    public interface TestBuilderCreatedAt {

        TestBuilder createdAt(LocalDate createdAt);
    }

    public interface TestBuilder {

        Test build();
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

    @Override
    public int compareTo(Test o) {
        return this.createdAt.compareTo(o.createdAt);
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

    @Override
    public String toString() {
        return "Test{" +
                "title='" + title + '\'' +
                ", countOfQuestions=" + countOfQuestions +
                ", questionsList=" + questionsList +
                ", createdAt=" + createdAt +
                '}';
    }
}
