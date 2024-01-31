package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.VIEW_TESTS;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.CREATE_GROUP;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.CREATE_TEST;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.DELETE_REPORT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.DELETE_RESULT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.USERNAME;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.ROLE;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.EXIT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.RENAME_REPORT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.RENAME_RESULT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.VIEW_RESULTS;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.VIEW_REPORTS;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.JOIN_THE_GROUP;

import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MainMenuView implements Renderable {

    private final TestService testService;
    private final ResultService resultService;
    private final GroupService groupService;
    private final UserService userService;
    private final ReportService reportService;
    private final AuthService authService;

    private final TestMenuView testMenuView;
    private final ResultMenuView resultMenuView;
    private final ReportMenuView reportMenuView;

    private User currentUser;

    public MainMenuView(TestService testService, ResultService resultService,
            ReportService reportService, UserService userService,
            GroupService groupService, AuthService authService, TestMenuView testMenuView, ResultMenuView resultMenuView, ReportMenuView reportMenuView) {
        this.testService = testService;
        this.resultService = resultService;
        this.reportService = reportService;
        this.groupService = groupService;
        this.authService = authService;
        this.userService = userService;

        this.testMenuView = testMenuView;
        this.resultMenuView = resultMenuView;
        this.reportMenuView = reportMenuView;
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        currentUser = authService.getUser();

        testMenuView.setMainMenuView(this);
        resultMenuView.setMainMenuView(this);
        reportMenuView.setMainMenuView(this);


        testMenuView.setCurrentUser(authService.getUser());
        resultMenuView.setCurrentUser(authService.getUser());
        reportMenuView.setCurrentUser(authService.getUser());

        ConsolItems.clearConsole();

        System.out.println(USERNAME.getName() + currentUser.getUsername()
                + " " + ROLE.getName() + currentUser.getRole());

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("tester-menu")
                .message("Tester Menu");

        listPromptBuilder.newItem(VIEW_TESTS.toString()).text(VIEW_TESTS.getName()).add();
        listPromptBuilder.newItem(CREATE_TEST.toString()).text(CREATE_TEST.getName()).add();
        listPromptBuilder.newItem(JOIN_THE_GROUP.toString()).text(JOIN_THE_GROUP.getName()).add();
        listPromptBuilder.newItem(CREATE_GROUP.toString()).text(CREATE_GROUP.getName()).add();
        listPromptBuilder.newItem(VIEW_RESULTS.toString()).text(VIEW_RESULTS.getName()).add();
        listPromptBuilder.newItem(VIEW_REPORTS.toString()).text(VIEW_REPORTS.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("tester-menu");

        MainMenu selectedItem = MainMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void process(MainMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {

            case VIEW_TESTS -> {
                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .name("tests-list").message("List of tests:");

                Set<Test> tests = testService.getAll();
                if (Objects.isNull(tests)) {
                    System.out.println("But no one came");

                } else {
                    for (Test test : tests) {
                        listPromptBuilder.newItem(test.getTitle())
                                .text(test.getTitle())
                                .add();
                    }
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());

                ListResult resultInput = (ListResult) result.get("tests-list");

                if (resultInput.getSelectedId().equals("back")) {
                    ConsolItems.clearConsole();
                    this.render();
                }

                Test userTest = testService.findByTitle(resultInput.getSelectedId());

                System.out.println(userTest.toString());

                testMenuView.setCurrentTest(userTest);
                testMenuView.render();
            }
            case CREATE_TEST -> {
                boolean dataCorrect = true;

                if (!currentUser.getRole().getPermissions().get(EntityName.TEST).canAdd()) {

                    System.out.println("Access to create tests is denied");
                    System.console().reader().read();
                    this.render();
                }

                do {
                    promptBuilder.createInputPrompt()
                            .name("test-name")
                            .message("Type test name: ")
                            .addPrompt();
                    promptBuilder.createInputPrompt()
                            .name("questions-count")
                            .message("Type count of questions: ")
                            .addPrompt();

                    try {
                        var result = prompt.prompt(promptBuilder.build());

                        var testTitleInput = (InputResult) result.get("test-name");
                        var countInput = (InputResult) result.get("questions-count");
                        int countOfQuestions = 0;
                        String testTitle;
                        try {
                            testTitle = testTitleInput.getInput();
                        } catch (EntityArgumentException e) {
                            dataCorrect = false;
                            continue;
                        }
                        try {
                            countOfQuestions = Integer.parseInt(countInput.getInput());
                        } catch (NumberFormatException e) {
                            System.out.println("Wrong count of questions format");
                            dataCorrect = false;
                            continue;
                        }

                        Set<Answer> correctAnswers = new HashSet<>();
                        Set<Question> questions = new HashSet<>();

                        prompt = new ConsolePrompt();
                        promptBuilder = new PromptBuilder();
                        for (int i = 0; i < countOfQuestions; i++) {
                            promptBuilder.createInputPrompt()
                                    .name("question-text")
                                    .message("Type question text: ")
                                    .addPrompt();

                            var questionResult = prompt.prompt(promptBuilder.build());

                            var questionTextInput = (InputResult) questionResult.get(
                                    "question-text");

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

                            Question question = Question.builder().id(UUID.randomUUID())
                                    .text(questionTextInput.getInput()).answers(answerList)
                                    .correctAnswer(correctAnswer)
                                    .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES))
                                    .build();

                            questions.add(question);
                            correctAnswers.add(correctAnswer);

                            prompt = new ConsolePrompt();
                            promptBuilder = new PromptBuilder();

                            ConsolItems.clearConsole();
                        }
                        LocalDateTime createdAt = LocalDateTime.now()
                                .truncatedTo(ChronoUnit.MINUTES);

                        TestAddDto testAddDto = new TestAddDto(UUID.randomUUID(),
                                testTitle, countOfQuestions, questions,
                                correctAnswers, createdAt);
                        try {

                            testService.add(testAddDto);
                            jsonRepositoryFactory.commit();

                            ConsolItems.clearConsole();
                            System.out.println(
                                    testTitleInput.getInput() + " test successfully created");
                            dataCorrect = true;
                        } catch (SignUpException e) {
                            promptBuilder = prompt.getPromptBuilder();
                            ConsolItems.clearConsole();
                            System.out.println(e.getMessage());
                            dataCorrect = false;
                        }
                    } catch (EntityArgumentException e) {
                        promptBuilder = prompt.getPromptBuilder();
                        ConsolItems.clearConsole();
                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);

                this.render();
            }

            case JOIN_THE_GROUP -> {
                if (!Objects.isNull(groupService.findByUser(currentUser))) {
                    groupService.findByUser(currentUser).removeObserver(currentUser);
                }

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .name("groups-list").message("List of groups:");

                Set<Group> groups = groupService.getAll();
                for (Group group : groups) {
                    listPromptBuilder.newItem(group.getName()).text(group.getName()).add();
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());
                ListResult groupInput = (ListResult) result.get("groups-list");

                if (groupInput.getSelectedId().equals("back")) {
                    ConsolItems.clearConsole();
                    this.render();
                }

                Group group = groupService.findByName(groupInput.getSelectedId());

                group.addObserver(currentUser);
                jsonRepositoryFactory.commit();

                System.out.println(group.getUsers());

                ConsolItems.clearConsole();
                System.out.println("You have joined the group " + group.getName());

                this.render();
            }
            case CREATE_GROUP -> {
                if (!currentUser.getRole().getPermissions().get(EntityName.GROUP).canAdd()) {
                    System.out.println("Access to create groups is denied");
                    System.console().reader().read();
                    this.render();
                }

                boolean dataCorrect = true;

                do {
                    promptBuilder.createInputPrompt()
                            .name("group-name")
                            .message("Type group name: ")
                            .addPrompt();

                    try {
                        var result = prompt.prompt(promptBuilder.build());

                        var groupNameInput = (InputResult) result.get("group-name");
                        LocalDateTime createdAt = LocalDateTime.now()
                                .truncatedTo(ChronoUnit.MINUTES);

                        GroupAddDto groupAddDto = new GroupAddDto(UUID.randomUUID(),
                                groupNameInput.getInput(), null, createdAt);
                        try {
                            groupService.add(groupAddDto);
                            jsonRepositoryFactory.commit();

                            ConsolItems.clearConsole();
                            System.out.println(
                                    groupNameInput.getInput() + " group successfully created");
                            dataCorrect = true;
                        } catch (SignUpException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        promptBuilder = prompt.getPromptBuilder();
                        ConsolItems.clearConsole();
                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);

                this.render();
            }
            case VIEW_RESULTS -> {
                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .name("results-list").message("List of results:");

                Set<Result> results = resultService.findAllByUsername(currentUser.getUsername());
                if (Objects.isNull(results)) {
                    System.out.println("But no one came");

                } else {
                    for (Result result : results) {
                        listPromptBuilder.newItem(result.getResultTitle())
                                .text(result.getResultTitle())
                                .add();
                    }
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());

                ListResult resultInput = (ListResult) result.get("results-list");

                if (resultInput.getSelectedId().equals("back")) {
                    ConsolItems.clearConsole();
                    this.render();
                }

                Result userResult = resultService.findByName(resultInput.getSelectedId());

                System.out.println(userResult.toString());

                resultMenuView.setCurrentResult(userResult);
                resultMenuView.render();
            }
            case VIEW_REPORTS -> {
                if (!currentUser.getRole().getPermissions().get(EntityName.REPORT).canRead()) {
                    System.out.println("Access to view reports is denied");
                    System.console().reader().read();
                    this.render();
                }

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .name("reports-list").message("List of reports:");

                Set<Report> reports = reportService.getAll();
                for (Report report : reports) {
                    listPromptBuilder.newItem(report.getReportTitle()).text(report.getReportTitle())
                            .add();
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());
                ListResult reportInput = (ListResult) result.get("reports-list");

                if (reportInput.getSelectedId().equals("back")) {
                    ConsolItems.clearConsole();
                    this.render();
                }

                Report userReport = reportService.findByName(reportInput.getSelectedId());

                System.out.println(userReport.toString());

                reportMenuView.setCurrentReport(userReport);
                reportMenuView.render();
            }
            case EXIT -> {
            }
        }
    }

    enum MainMenu {
        CREATE_TEST("Create test"),
        VIEW_TESTS("View tests"),
        JOIN_THE_GROUP("Join the group"),
        CREATE_GROUP("Create new group"),
        VIEW_RESULTS("View your results"),
        RENAME_RESULT("Rename result"),
        DELETE_RESULT("Delete result"),
        VIEW_REPORTS("View your reports"),
        RENAME_REPORT("Rename report"),
        DELETE_REPORT("Delete report"),
        USERNAME("Username: "),
        ROLE("Role: "),
        EXIT("Exit");

        private final String name;

        MainMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
