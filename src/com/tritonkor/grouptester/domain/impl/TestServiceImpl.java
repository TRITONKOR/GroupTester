package com.tritonkor.grouptester.domain.impl;

import static java.lang.System.out;

import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Supplier;

public class TestServiceImpl
        extends GenericService<Test>
        implements TestService {

    private final TestRepository testRepository;

    private  User userForTesting = null;

    private Grade grade;

    public TestServiceImpl(TestRepository testRepository) {
        super(testRepository);
        this.testRepository = testRepository;
    }

    public User getUserForTesting() {
        return userForTesting;
    }

    public void setUserForTesting(User userForTesting) {
        this.userForTesting = userForTesting;
    }

    @Override
    public Optional<Test> findByTitle(String title) {
        return testRepository.findByTitle(title);
    }

    public Answer getUserAnswer(Supplier<Integer> waitForUserInput, Question question) {
        int userVariant = waitForUserInput.get();
        return question.getAnswers().get(userVariant - 1);
    }

    public Grade calculateGrade(Test test, Set<Answer> userAnswers) {
        userAnswers.retainAll(test.getCorrectAnswers());
        return new Grade(Math.round(100 / test.getCountOfQuestions()) * userAnswers.size());
    }

    public void runTest(Test test, ResultServiceImpl resultService) {
        Set<Answer> userAnswers = new HashSet<>();

        out.println("Test:" + test.getTitle());

        for(Question question : test.getQuestionsList()) {
            out.println("Введіть номер відповіді");
            out.println(question.toString());
            for(Answer answer : question.getAnswers()) {
                out.println(answer);
            }

            userAnswers.add(getUserAnswer((() -> {
                Scanner scanner = new Scanner(System.in);
                return scanner.nextInt();
            }) , question));
        }

        grade = calculateGrade(test, userAnswers);

        resultService.makeResult(userForTesting.getUsername(), test.getTitle(), grade);

    }

    private void resetGrade() {
        grade = null;
    }
}