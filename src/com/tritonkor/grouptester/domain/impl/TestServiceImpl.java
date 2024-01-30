package com.tritonkor.grouptester.domain.impl;

import static java.lang.System.out;

import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import org.mindrot.bcrypt.BCrypt;

public class TestServiceImpl
        extends GenericService<Test>
        implements TestService {

    private final TestRepository testRepository;

    private Grade grade;

    public TestServiceImpl(TestRepository testRepository) {
        super(testRepository);
        this.testRepository = testRepository;
    }

    @Override
    public Answer findCorrectAnswer(List<Answer> answerList, String text) {
        return answerList.stream().filter(a -> a.getText().equals(text)).findFirst().orElse(null);
    }

    @Override
    public Test findByTitle(String title) {
        return testRepository.findByTitle(title).orElse(null);
    }

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

    public Grade calculateGrade(Test test, Set<Answer> userAnswers) {
        userAnswers.retainAll(test.getCorrectAnswers());
        return new Grade(Math.round(100 / test.getCountOfQuestions()) * userAnswers.size());
    }

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

            out.println('\n');
            prompt = new ConsolePrompt();
            promptBuilder = prompt.getPromptBuilder();
        }

        grade = calculateGrade(test, userAnswers);
        return grade;
    }

    private void resetGrade() {
        grade = null;
    }

    private Answer findAnswerByText(Test test, String text) {
        return  test.getQuestionsList().stream()
                .flatMap(question -> question.getAnswers().stream())
                .filter(answer -> answer.getText().equals(text))
                .findFirst().orElse(null);
    }
}