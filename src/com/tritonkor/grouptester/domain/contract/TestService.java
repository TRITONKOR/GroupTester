package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import java.io.IOException;
import java.util.List;

public interface TestService extends Service<Test> {

    Test findByTitle(String title);

    Test add(TestAddDto testAddDto);

    Grade runTest(Test test) throws IOException;

    Answer findCorrectAnswer(List<Answer> answerList, String text);

    Question findQuestion(Test test, String text);

    void renameTest(Test test, String newTitle);

    void addQuestionToTest(Test test, String questionText, List<Answer> answers, Answer correctAnswer);

    void removeQuestionFromTest(Test test, Question question);
}
