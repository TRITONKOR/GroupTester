package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * The {@code Test} class represents a test in the system.
 */
public class Test extends Entity {

    private String title;

    private int countOfQuestions;

    private Set<Question> questions;
    private Set<Answer> correctAnswers;

    private final LocalDateTime createdAt;

    /**
     * Constructs a new {@code Test} instance.
     *
     * @param id                The unique identifier for the test.
     * @param title             The title of the test.
     * @param countOfQuestions  The count of questions in the test.
     * @param questions         The set of questions in the test.
     * @param correctAnswers    The set of correct answers in the test.
     * @param createdAt         The creation timestamp of the test.
     */
    private Test(UUID id, String title, int countOfQuestions, Set<Question> questions, Set<Answer> correctAnswers,
            LocalDateTime createdAt) {
        super(id);
        this.title = title;
        this.countOfQuestions = countOfQuestions;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code TestBuilderId} instance to start building a {@code Test}.
     *
     * @return A {@code TestBuilderId} instance.
     */
    public static TestBuilderId builder() {
        return id -> title -> countOfQuestions -> questions -> correctAnswers -> createdAt -> () -> new Test(id,
                title, countOfQuestions, questions, correctAnswers, createdAt);
    }

    /**
     * Interface for the {@code Test} builder to set the ID.
     */
    public interface TestBuilderId {

        TestBuilderTitle id(UUID id);
    }

    /**
     * Interface for the {@code Test} builder to set the title.
     */
    public interface TestBuilderTitle {

        TestBuilderCountOfQuestions title(String title);
    }

    /**
     * Interface for the {@code Test} builder to set the count of questions.
     */
    public interface TestBuilderCountOfQuestions {

        TestBuilderQuestions countOfQuestionsle(int countOfQuestions);
    }

    /**
     * Interface for the {@code Test} builder to set the questions.
     */
    public interface TestBuilderQuestions {

        TestBuilderCorrectAnswers questions(Set<Question> questions);
    }

    /**
     * Interface for the {@code Test} builder to set the correct answers.
     */
    public interface TestBuilderCorrectAnswers {

        TestBuilderCreatedAt correctAnswers(Set<Answer> correctAnswers);
    }

    /**
     * Interface for the {@code Test} builder to set the creation date.
     */
    public interface TestBuilderCreatedAt {

        TestBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Test} builder.
     */
    public interface TestBuilder {

        Test build();
    }

    /**
     * Gets the title of the test.
     *
     * @return The test's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the test.
     *
     * @param title The new title for the test.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the count of questions in the test.
     *
     * @return The count of questions.
     */
    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    /**
     * Sets the count of questions in the test.
     *
     * @param countOfQuestions The new count of questions.
     */
    public void setCountOfQuestions(int countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }

    /**
     * Gets the set of questions in the test.
     *
     * @return The set of questions.
     */
    public Set<Question> getQuestionsList() {
        return questions;
    }

    /**
     * Sets the set of questions in the test.
     *
     * @param questions The new set of questions.
     */
    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    /**
     * Gets the creation timestamp of the test.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the set of correct answers in the test.
     *
     * @return The set of correct answers.
     */
    public Set<Answer> getCorrectAnswers() {
        return correctAnswers;
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
