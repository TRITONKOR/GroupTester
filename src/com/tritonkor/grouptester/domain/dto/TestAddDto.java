package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


/**
 * The TestAddDto class represents a data transfer object for adding a test. It extends the Entity
 * class, providing identification through a UUID.
 */
public class TestAddDto extends Entity {

    private String title;
    private int countOfQuestions;
    private Set<Question> questions;
    private Set<Answer> correctAnswers;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new TestAddDto with the specified parameters.
     *
     * @param id               the unique identifier for the test.
     * @param title            the title of the test.
     * @param countOfQuestions the count of questions in the test.
     * @param questions        the set of questions in the test.
     * @param correctAnswers   the set of correct answers in the test.
     * @param createdAt        the date and time when the test was created.
     */
    public TestAddDto(UUID id, String title, int countOfQuestions, Set<Question> questions,
            Set<Answer> correctAnswers, LocalDateTime createdAt) {
        super(id);
        this.title = ValidationUtil.validateTitle(title);
        this.countOfQuestions = countOfQuestions;
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    /**
     * Gets the title of the test.
     *
     * @return the title of the test.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the count of questions in the test.
     *
     * @return the count of questions in the test.
     */
    public int getCountOfQuestions() {
        return countOfQuestions;
    }

    /**
     * Gets the set of questions in the test.
     *
     * @return the set of questions in the test.
     */
    public Set<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets the set of correct answers in the test.
     *
     * @return the set of correct answers in the test.
     */
    public Set<Answer> getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     * Gets the date and time when the test was created.
     *
     * @return the date and time when the test was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
