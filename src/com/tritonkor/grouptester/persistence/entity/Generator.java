package com.tritonkor.grouptester.persistence.entity;

import com.github.javafaker.Faker;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Generator {

    public static Set<User> generateUsers(int count) {
        Set<User> users = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password(8, 32 , true, true, true);
            LocalDate birthday = faker.date()
                    .birthday()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            User user = User.builder().id(id).username(username).email(email).password(password)
                    .birthday(birthday).build();
            users.add(user);
        }

        return users;
    }

    public static Set<Group> generateGroups(int count) {
        Set<Group> groups = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String name = faker.lorem().characters(5);
            Set<User> users = generateUsers(4);
            LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

            Group group = Group.builder().id(id).name(name).users(users).createdAt(createdAt)
                    .build();
            groups.add(group);
        }

        return groups;
    }

    public static Set<Test> generateTests(int count) {
        Set<Test> tests = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String title = faker.lorem().sentence(2);
            Set<Question> questionList = generateQuestions(3);
            LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            Test test = Test.builder().id(id).title(title).countOfQuestionsle(questionList.size())
                    .questions(questionList).createdAt(createdAt).build();
            tests.add(test);
        }

        return tests;
    }

    private static Set<Question> generateQuestions(int questionCount) {
        Set<Question> questionList = new HashSet<>();
        Faker faker = new Faker();

        for (int i = 0; i < questionCount; i++) {
            UUID id = UUID.randomUUID();
            String text = faker.lorem().sentence(15);
            Set<Answer> answers = generateAnswers(3);
            Answer[] array = answers.toArray(new Answer[0]);
            Answer correctAnswer = array[0];
            LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            Question question = Question.builder().id(id).text(text)
                    .answers(answers).correctAnswer(correctAnswer).createdAt(createdAt)
                    .build();
            questionList.add(question);
        }

        return questionList;
    }

    private static Set<Answer> generateAnswers(int answerCount) {
        Set<Answer> answers = new HashSet<>();

        Faker faker = new Faker();

        for (int i = 0; i < answerCount; i++) {
            UUID id = UUID.randomUUID();
            String text = faker.lorem().sentence(5);

            LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            Answer answer = Answer.builder().id(id).text(text).createdAt(createdAt).build();
            answers.add(answer);
        }

        return answers;
    }

    public static Set<Result> generateResults(int resultCount) {
        Set<Result> results = new HashSet<>();

        Faker faker = new Faker();

        for (int i = 0; i < resultCount; i++) {
            UUID id = UUID.randomUUID();
            User[] userset = generateUsers(1).toArray(new User[0]);
            User owner = userset[0];

            String testTitle = faker.lorem().sentence(2);

            int mark = (int) Math.floor(Math.random() * 100 + 1);

            LocalDateTime createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

            Result result = Result.builder().id(id).owner(owner).mark(mark).testTitle(testTitle)
                    .createdAt(createdAt).build();
            results.add(result);
        }

        return results;
    }
}
