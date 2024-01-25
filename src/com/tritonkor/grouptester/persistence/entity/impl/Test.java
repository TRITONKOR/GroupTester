package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;

import com.tritonkor.grouptester.domain.Validation;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Test extends Entity implements Comparable<Test> {

    private String title;

    private int countOfQuestions;

    private Set<Question> questions;

    private final LocalDateTime createdAt;

    private Test(UUID id, String title, int countOfQuestions, Set<Question> questions,
            LocalDateTime createdAt) {
        super(id);
        this.title = Validation.validateText(title, errors, 100);
        this.countOfQuestions = countOfQuestions;
        this.questions = questions;
        this.createdAt = Validation.validateDateTime(createdAt, errors);
    }

    public static TestBuilderId builder() {
        return id -> title -> countOfQuestions -> questions -> createdAt -> () -> new Test(id,
                title, countOfQuestions, questions, createdAt);
    }

    public interface TestBuilderId {

        TestBuilderTitle id(UUID id);
    }

    public interface TestBuilderTitle {

        TestBuilderCountOfQuestions title(String title);
    }

    public interface TestBuilderCountOfQuestions {

        TestBuilderQuestions countOfQuestionsle(int countOfQuestions);
    }

    public interface TestBuilderQuestions {

        TestBuilderCreatedAt questions(Set<Question> questions);
    }

    public interface TestBuilderCreatedAt {

        TestBuilder createdAt(LocalDateTime createdAt);
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

    public Set<Question> getQuestionsList() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public LocalDateTime getCreatedAt() {
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
                ", questions=" + questions +
                ", createdAt=" + createdAt +
                '}';
    }
}
