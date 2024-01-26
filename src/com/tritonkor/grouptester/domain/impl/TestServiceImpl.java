package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class TestServiceImpl
        extends GenericService<Test>
        implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        super(testRepository);
        this.testRepository = testRepository;
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
}