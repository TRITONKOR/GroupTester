package com.tritonkor.grouptester.domain.impl;

import static java.lang.System.out;

import com.tritonkor.grouptester.appui.ConsolItems;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * The TestServiceImpl class is an implementation of the TestService interface, providing
 * functionality related to managing tests and running test sessions in the application.
 */
public class TestServiceImpl
        extends GenericService<Test>
        implements TestService {

    private final TestRepository testRepository;

    private Grade grade;

    /**
     * Constructs a new TestServiceImpl with the specified TestRepository.
     *
     * @param testRepository the repository used for test-related operations.
     */
    public TestServiceImpl(TestRepository testRepository) {
        super(testRepository);
        this.testRepository = testRepository;
    }

    /**
     * Finds the correct answer from a list of answers based on the provided text.
     *
     * @param answerList the list of answers to search.
     * @param text       the text of the correct answer to find.
     * @return the correct answer or null if not found.
     */
    @Override
    public Answer findCorrectAnswer(List<Answer> answerList, String text) {
        return answerList.stream().filter(a -> a.getText().equals(text)).findFirst().orElse(null);
    }

    /**
     * Finds a question in a test based on the provided question text.
     *
     * @param test         the test containing the questions.
     * @param questionText the text of the question to find.
     * @return the found question or null if not found.
     */
    @Override
    public Question findQuestion(Test test, String questionText) {
        return test.getQuestionsList().stream().filter(q -> q.getText().equals(questionText))
                .findFirst().orElse(null);
    }

    /**
     * Finds a test by its title.
     *
     * @param title the title of the test to find.
     * @return the found test or null if not found.
     */
    @Override
    public Test findByTitle(String title) {
        return testRepository.findByTitle(title).orElse(null);
    }

    /**
     * Adds a new test based on the provided TestAddDto.
     *
     * @param testAddDto the details of the test to add.
     * @return the added test.
     * @throws SignUpException if there is an error when saving the test.
     */
    @Override
    public Test add(TestAddDto testAddDto) {
        try {
            var test = Test.builder().id(testAddDto.getId()).title(testAddDto.getTitle())
                    .countOfQuestionsle(testAddDto.getCountOfQuestions())
                    .questions(testAddDto.getQuestions())
                    .correctAnswers(testAddDto.getCorrectAnswers())
                    .createdAt(testAddDto.getCreatedAt()).build();
            testRepository.add(test);
            return test;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving a test: %s"
                    .formatted(e.getMessage()));
        }
    }

    /**
     * Calculates the grade for a user based on their answers in a test.
     *
     * @param test        the test for which to calculate the grade.
     * @param userAnswers the user's answers in the test.
     * @return the calculated grade.
     */
    public Grade calculateGrade(Test test, Set<Answer> userAnswers) {
        userAnswers.retainAll(test.getCorrectAnswers());
        return new Grade((int) Math.round(100.0 / test.getCountOfQuestions()) * userAnswers.size());
    }

    /**
     * Runs a test session, prompting the user for answers and calculating the grade.
     *
     * @param test the test to run.
     * @return the calculated grade.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    public Grade runTest(Test test) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        Set<Answer> userAnswers = new HashSet<>();

        for (Question question : test.getQuestionsList()) {
            ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                    .name("test").message("Test: " + test.getTitle());

            out.println(question.getText());
            for (Answer answer : question.getAnswers()) {
                listPromptBuilder.newItem(answer.getText()).text(answer.getText()).add();
            }

            var result = prompt.prompt(listPromptBuilder.addPrompt().build());
            ListResult answerInput = (ListResult) result.get("test");

            Answer userAnswer = findAnswerByText(test, answerInput.getSelectedId().toString());

            userAnswers.add(userAnswer);

            ConsolItems.clearConsole();
            prompt = new ConsolePrompt();
            promptBuilder = prompt.getPromptBuilder();
        }

        grade = calculateGrade(test, userAnswers);
        return grade;
    }

    /**
     * Adds a new question to a test with the provided question text, answers, and correct answer.
     *
     * @param test          the test to which to add the question.
     * @param questionText  the text of the question.
     * @param answers       the list of answers for the question.
     * @param correctAnswer the correct answer for the question.
     */
    @Override
    public void addQuestionToTest(Test test, String questionText, List<Answer> answers,
            Answer correctAnswer) {
        Question question = Question.builder().id(UUID.randomUUID()).text(questionText)
                .answers(answers).correctAnswer(correctAnswer).createdAt(
                        LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)).build();

        Set<Question> questions = test.getQuestionsList();
        questions.add(question);

        Set<Answer> correctAnswers = test.getCorrectAnswers();
        correctAnswers.add(correctAnswer);

        test.setCountOfQuestions(questions.size());
    }

    /**
     * Removes a question from a test.
     *
     * @param test     the test from which to remove the question.
     * @param question the question to remove.
     */
    public void removeQuestionFromTest(Test test, Question question) {
        Set<Question> questions = test.getQuestionsList();
        questions.remove(question);

        Set<Answer> correctAnswers = test.getCorrectAnswers();
        Answer correctAnswer = question.getCorrectAnswer();
        correctAnswers.remove(correctAnswer);
    }

    /**
     * Renames a test with a new title.
     *
     * @param test     the test to rename.
     * @param newTitle the new title for the test.
     */
    @Override
    public void renameTest(Test test, String newTitle) {
        TestAddDto testAddDto = new TestAddDto(test.getId(), newTitle, test.getCountOfQuestions(),
                test.getQuestionsList(), test.getCorrectAnswers(), test.getCreatedAt());
        this.remove(test);
        this.add(testAddDto);
    }

    /**
     * Finds an answer in a test based on the provided text.
     *
     * @param test the test containing the answers.
     * @param text the text of the answer to find.
     * @return the found answer or null if not found.
     */
    private Answer findAnswerByText(Test test, String text) {
        return test.getQuestionsList().stream()
                .flatMap(question -> question.getAnswers().stream())
                .filter(answer -> answer.getText().equals(text))
                .findFirst().orElse(null);
    }
}