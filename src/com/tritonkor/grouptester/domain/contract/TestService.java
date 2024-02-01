package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import java.io.IOException;
import java.util.List;

/**
 * The TestService interface defines the contract for managing tests in the application. It provides
 * methods for retrieving, adding, running tests, and managing test-related operations.
 */
public interface TestService extends Service<Test> {

    /**
     * Finds a test by its title.
     *
     * @param title The title of the test to find.
     * @return The found test or null if not found.
     */
    Test findByTitle(String title);

    /**
     * Adds a new test based on the provided TestAddDto.
     *
     * @param testAddDto The DTO containing information to create the new test.
     * @return The added test.
     */
    Test add(TestAddDto testAddDto);

    /**
     * Runs a test session, prompting the user for answers and calculating the grade.
     *
     * @param test The test to be taken by the user.
     * @return The calculated grade for the user's responses.
     * @throws IOException If an I/O error occurs during the test session.
     */
    Grade runTest(Test test) throws IOException;

    /**
     * Finds the correct answer from a list of answers based on the provided text.
     *
     * @param answerList The list of answers to search.
     * @param text       The text of the correct answer.
     * @return The correct answer or null if not found.
     */
    Answer findCorrectAnswer(List<Answer> answerList, String text);

    /**
     * Finds a question in a test based on the provided question text.
     *
     * @param test The test containing the questions.
     * @param text The text of the question to find.
     * @return The found question or null if not found.
     */
    Question findQuestion(Test test, String text);

    /**
     * Renames a test with a new title.
     *
     * @param test     The test to be renamed.
     * @param newTitle The new title for the test.
     */
    void renameTest(Test test, String newTitle);

    /**
     * Adds a new question to a test with the provided question text, answers, and correct answer.
     *
     * @param test          The test to which the question will be added.
     * @param questionText  The text of the new question.
     * @param answers       The list of answers for the question.
     * @param correctAnswer The correct answer for the question.
     */
    void addQuestionToTest(Test test, String questionText, List<Answer> answers,
            Answer correctAnswer);

    /**
     * Removes a question from a test.
     *
     * @param test     The test from which the question will be removed.
     * @param question The question to be removed.
     */
    void removeQuestionFromTest(Test test, Question question);
}
