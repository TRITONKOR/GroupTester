package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.TestMenuView.TestMenu.RUN_TEST;
import static com.tritonkor.grouptester.appui.TestMenuView.TestMenu.RENAME_TEST;
import static com.tritonkor.grouptester.appui.TestMenuView.TestMenu.ADD_QUESTION;
import static com.tritonkor.grouptester.appui.TestMenuView.TestMenu.DELETE_QUESTION;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.ROLE;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.USERNAME;
import static com.tritonkor.grouptester.appui.TestMenuView.TestMenu.EXIT;

import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role.EntityName;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class TestMenuView implements Renderable{

    private final TestService testService;
    private final ResultService resultService;
    private final GroupService groupService;
    private final UserService userService;
    private final ReportService reportService;

    private MainMenuView mainMenuView;

    private User currentUser;
    private Test currentTest;

    public TestMenuView(TestService testService, ResultService resultService,
            GroupService groupService,
            UserService userService, ReportService reportService) {
        this.testService = testService;
        this.resultService = resultService;
        this.groupService = groupService;
        this.userService = userService;
        this.reportService = reportService;
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ConsolItems.clearConsole();

        System.out.println(USERNAME.getName() + currentUser.getUsername()
                + " " + ROLE.getName() + currentUser.getRole());

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("tester-menu")
                .message("Test Menu");

        listPromptBuilder.newItem(TestMenu.RUN_TEST.toString()).text(RUN_TEST.getName()).add();
        listPromptBuilder.newItem(RENAME_TEST.toString()).text(RENAME_TEST.getName()).add();
        listPromptBuilder.newItem(ADD_QUESTION.toString()).text(ADD_QUESTION.getName()).add();
        listPromptBuilder.newItem(DELETE_QUESTION.toString()).text(DELETE_QUESTION.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("tester-menu");

        TestMenu selectedItem = TestMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void process(TestMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {

            case RUN_TEST -> {
                if (Objects.isNull(groupService.findByUser(currentUser))) {
                    ConsolItems.clearConsole();
                    System.out.println("You must join a group before taking the tests");

                    this.render();
                }

                ConsolItems.clearConsole();

                Grade userGrade = testService.runTest(currentTest);

                boolean correctData = true;
                do {
                    promptBuilder.createInputPrompt()
                            .name("result-name")
                            .message("Type result name: ")
                            .addPrompt();
                    try {
                        var result = prompt.prompt(promptBuilder.build());

                        var resultNameInput = (InputResult) result.get("result-name");

                        ResultAddDto resultAddDto = new ResultAddDto(UUID.randomUUID(),
                                resultNameInput.getInput(),
                                currentUser.getUsername(), userGrade, currentTest.getTitle(),
                                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                        resultService.add(resultAddDto);

                        Group userGroup = groupService.findByUser(currentUser);
                        Set<User> users = userService.getAll();
                        System.out.println(users);
                        users.remove(currentUser);

                        HashMap<String, Grade> usersResults = new HashMap<>();
                        Random random = new Random();
                        for(User user : users) {
                            usersResults.put(user.getUsername(), new Grade(random.nextInt(100) + 1));
                        }
                        usersResults.put(currentUser.getUsername(), userGrade);

                        promptBuilder = new PromptBuilder();

                        promptBuilder.createInputPrompt()
                                .name("report-name")
                                .message("Type report name: ")
                                .addPrompt();

                        result = prompt.prompt(promptBuilder.build());

                        resultNameInput = (InputResult) result.get("report-name");

                        ReportAddDto reportAddDto = reportService.makeReport(resultNameInput.getInput(), userGroup.getName(),
                                currentTest.getTitle(), usersResults);
                        reportService.add(reportAddDto);

                        jsonRepositoryFactory.commit();

                        ConsolItems.clearConsole();
                        System.out.println(
                                "Result " + resultNameInput.getInput() + "has been saved");

                        correctData = true;
                    } catch (EntityArgumentException e) {
                        ConsolItems.clearConsole();
                        System.out.println(e.getMessage());

                        correctData = false;
                    }
                } while (!correctData);

                this.render();
            }
            case RENAME_TEST -> {
                if (!currentUser.getRole().getPermissions().get(EntityName.TEST).canEdit()) {

                    System.out.println("Access to create tests is denied");
                    System.console().reader().read();
                    this.render();
                }

                promptBuilder.createInputPrompt()
                        .name("test-name")
                        .message("Type name for test: ")
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());

                var reportNameInput = (InputResult) result.get("test-name");

                testService.renameTest(currentTest, reportNameInput.getInput());
                jsonRepositoryFactory.commit();

                ConsolItems.clearConsole();
                System.out.println("Set new name for report: " + reportNameInput.getInput());

                setCurrentTest(currentTest);
                this.render();
            }

            case ADD_QUESTION -> {
                if (!currentUser.getRole().getPermissions().get(EntityName.TEST).canEdit()) {

                    System.out.println("Access to create tests is denied");
                    System.console().reader().read();
                    this.render();
                }

                promptBuilder.createInputPrompt()
                        .name("question-text")
                        .message("Type question text: ")
                        .addPrompt();

                var questionResult = prompt.prompt(promptBuilder.build());

                var questionTextInput = (InputResult) questionResult.get("question-text");

                List<Answer> answerList = new ArrayList<>();
                prompt = new ConsolePrompt();
                promptBuilder = new PromptBuilder();

                for (int j = 0; j < 3; j++) {
                    promptBuilder.createInputPrompt()
                            .name("answer-text")
                            .message("Type answer text: ")
                            .addPrompt();

                    var answerResult = prompt.prompt(promptBuilder.build());

                    var answerTextInput = (InputResult) answerResult.get(
                            "answer-text");

                    Answer answerAddDto = Answer.builder().id(UUID.randomUUID())
                            .text(answerTextInput.getInput()).createdAt(
                                    LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                            .build();
                    answerList.add(answerAddDto);

                    prompt = new ConsolePrompt();
                    promptBuilder = new PromptBuilder();
                }

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .message("choose correct answer: ").name("correct-answer");
                for (Answer answer : answerList) {
                    listPromptBuilder.newItem(answer.getText()).text(answer.getText())
                            .add();
                }
                var correctAnswerResult = prompt.prompt(
                        listPromptBuilder.addPrompt().build());

                var correctAnswerInput = (ListResult) correctAnswerResult.get(
                        "correct-answer");

                Answer correctAnswer = testService.findCorrectAnswer(answerList,
                        correctAnswerInput.getSelectedId());

                testService.addQuestionToTest(currentTest, questionTextInput.getInput(), answerList,
                        correctAnswer);
                jsonRepositoryFactory.commit();

                this.render();
            }

            case DELETE_QUESTION -> {
                if (!currentUser.getRole().getPermissions().get(EntityName.TEST).canEdit()) {

                    System.out.println("Access to create tests is denied");
                    System.console().reader().read();
                    this.render();
                }

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt().name("questions-list")
                        .message("List of questions: ");

                Set<Question> questions = currentTest.getQuestionsList();

                for (Question question : questions) {
                    listPromptBuilder.newItem(question.getText()).text(question.getText()).add();
                }

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());

                var questionInput = (ListResult) result.get("questions-list");

                Question userQuestion = testService.findQuestion(currentTest,
                        questionInput.getSelectedId());

                promptBuilder.createConfirmPromp()
                        .name("delete-question")
                        .message("Are you that you want delete this question?")
                        .defaultValue(ConfirmChoice.ConfirmationValue.NO)
                        .addPrompt();

                result = prompt.prompt(promptBuilder.build());

                var userChoice = (ConfirmResult) result.get("delete-question");

                if (userChoice.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                    testService.removeQuestionFromTest(currentTest, userQuestion);
                    jsonRepositoryFactory.commit();

                    ConsolItems.clearConsole();
                    System.out.println("Question has been successfully deleted");
                    this.render();
                } else {
                    ConsolItems.clearConsole();
                    this.render();
                }
            }

            case EXIT -> {
                mainMenuView.render();
            }
        }
    }

    enum TestMenu {
        RUN_TEST("Run test"),
        RENAME_TEST("Rename test"),
        ADD_QUESTION("Add question"),
        DELETE_QUESTION("Delete question"),
        EXIT("Exit");

        private final String name;

        TestMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setCurrentTest(Test test) {
        this.currentTest = test;
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }
}

