package com.tritonkor.grouptester.persistence.entity;

import com.github.javafaker.Faker;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Generator {

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            LocalDate birthday = faker.date()
                    .birthday()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            User user = new User(id, username, email, password, birthday);
            users.add(user);
        }

        return users;
    }

    public static List<Test> generateTests(int count) {
        List<Test> tests = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String title = faker.lorem().sentence(2);
            String password = faker.internet().password();
            List<Question> questionList = generateQuestions(10);
            LocalDate createdAt = LocalDate.now();

            Test test = Test.builder().id(id).title(title).countOfQuestionsle(questionList.size()).questionsList(questionList).createdAt(createdAt).build();
            tests.add(test);
        }

        return tests;
    }

    private static List<Question> generateQuestions(int questionCount) {
        List<Question> questionList = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < questionCount; i++) {
            UUID id = UUID.randomUUID();
            String text = faker.lorem().sentence(15);
            List<Answer> answerList = generateAnswers(3);
            Answer correctAnswer = answerList.get(0);
            LocalDate createdAt = LocalDate.now();

            Question question = Question.builder().id(id).text(text).createdAt(createdAt).answerList(answerList).correctAnswer(correctAnswer).build();
            questionList.add(question);
        }

        return questionList;
    }

    private static List<Answer> generateAnswers(int answerCount) {
        List<Answer> answerList = new ArrayList<>();

        Faker faker = new Faker();

        for (int i = 0; i < answerCount; i++) {
            UUID id = UUID.randomUUID();
            String text = faker.lorem().sentence(5);
            LocalDate createdAt = LocalDate.now();

            Answer answer = Answer.builder().id(id).text(text).createdAt(createdAt).build();
            answerList.add(answer);
        }

        return answerList;
    }
}
