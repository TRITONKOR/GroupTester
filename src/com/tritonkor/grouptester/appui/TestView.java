package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.CREATE_GROUP;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.CREATE_TEST;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.DELETE_REPORT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.DELETE_RESULT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.EXIT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.RENAME_REPORT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.RENAME_RESULT;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.RUN_TEST;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.VIEW_RESULTS;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.VIEW_REPORTS;
import static com.tritonkor.grouptester.appui.TestView.TesterMenu.JOIN_THE_GROUP;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.dto.GroupAddDto;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.security.Permission;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TestView implements Renderable {

    private final TestService testService;
    private final ResultService resultService;
    private final GroupService groupService;
    private final ReportService reportService;
    private final AuthService authService;

    private User currentUser;

    public TestView(TestService testService, ResultService resultService,
            ReportService reportService,
            GroupService groupService, AuthService authService) {
        this.testService = testService;
        this.resultService = resultService;
        this.reportService = reportService;
        this.groupService = groupService;
        this.authService = authService;
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        currentUser = authService.getUser();

        System.out.println(TesterMenu.USERNAME.getName() + currentUser.getUsername()
                + " " + TesterMenu.ROLE.getName() + currentUser.getRole());

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("tester-menu")
                .message("Tester Menu");

        listPromptBuilder.newItem(CREATE_TEST.toString()).text(CREATE_TEST.getName()).add();
        listPromptBuilder.newItem(RUN_TEST.toString()).text(RUN_TEST.getName()).add();
        listPromptBuilder.newItem(JOIN_THE_GROUP.toString()).text(JOIN_THE_GROUP.getName()).add();
        listPromptBuilder.newItem(CREATE_GROUP.toString()).text(CREATE_GROUP.getName()).add();
        listPromptBuilder.newItem(VIEW_RESULTS.toString()).text(VIEW_RESULTS.getName()).add();
        listPromptBuilder.newItem(VIEW_REPORTS.toString()).text(VIEW_REPORTS.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("tester-menu");

        TesterMenu selectedItem = TesterMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void process(TesterMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {

            case CREATE_TEST -> {
                boolean dataCorrect = true;

                if (currentUser.getRole().equals(Role.GENERAL)) {
                    ConsolClearer.clearConsole();

                    System.out.println("Access to create tests is denied");
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
                        } catch (EntityArgumentException e) {;
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

                            ConsolClearer.clearConsole();
                        }
                        LocalDateTime createdAt = LocalDateTime.now()
                                .truncatedTo(ChronoUnit.MINUTES);

                        TestAddDto testAddDto = new TestAddDto(UUID.randomUUID(),
                                testTitle, countOfQuestions, questions,
                                correctAnswers, createdAt);
                        try {

                            testService.add(testAddDto);
                            jsonRepositoryFactory.commit();

                            ConsolClearer.clearConsole();
                            System.out.println(
                                    testTitleInput.getInput() + " test successfully created");
                            dataCorrect = true;
                        } catch (SignUpException e) {
                            promptBuilder = prompt.getPromptBuilder();
                            ConsolClearer.clearConsole();
                            System.out.println(e.getMessage());
                            dataCorrect = false;
                        }
                    } catch (EntityArgumentException e) {
                        promptBuilder = prompt.getPromptBuilder();
                        ConsolClearer.clearConsole();
                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);
            }
            case RUN_TEST -> {
                if (groupService.findByUser(currentUser).equals(null)) {
                    ConsolClearer.clearConsole();
                    System.out.println("You must join a group before taking the tests");

                    this.render();
                }

                ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                        .name("tests-list").message("List of tests:");

                Set<Test> tests = testService.getAll();
                for (Test test : tests) {
                    listPromptBuilder.newItem(test.getTitle()).text(test.getTitle()).add();
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());
                ListResult testInput = (ListResult) result.get("tests-list");

                if (testInput.getSelectedId().equals("back")) {
                    this.render();
                }

                prompt = new ConsolePrompt();
                promptBuilder = prompt.getPromptBuilder();

                Test test = testService.findByTitle(testInput.getSelectedId());

                ConsolClearer.clearConsole();

                Grade userGrade = testService.runTest(test);
                String resultName = null;

                boolean correctData = true;
                do {
                    promptBuilder.createInputPrompt()
                            .name("result-name")
                            .message("Type result name: ")
                            .addPrompt();
                    try {
                        result = prompt.prompt(promptBuilder.build());

                        var resultNameInput = (InputResult) result.get("result-name");

                        System.out.println(resultNameInput.getInput());
                        resultName = ValidationUtil.validateName(resultNameInput.getInput());

                        ResultAddDto resultAddDto = new ResultAddDto(UUID.randomUUID(), resultName,
                                currentUser.getUsername(), userGrade, test.getTitle(),
                                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
                        resultService.add(resultAddDto);
                        jsonRepositoryFactory.commit();

                        correctData = true;
                    } catch (EntityArgumentException e) {
                        ConsolClearer.clearConsole();
                        System.out.println(e.getMessage());

                        correctData = false;
                    }
                } while (!correctData);

                ConsolClearer.clearConsole();
                System.out.println("Result " + resultName + "has been saved");
                this.render();
            }

            case JOIN_THE_GROUP -> {
                groupService.findByUser(currentUser).removeObserver(currentUser);

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
                    this.render();
                }

                Group group = groupService.findByName(groupInput.getSelectedId());

                group.addObserver(currentUser);
                System.out.println(group.getUsers());

                ConsolClearer.clearConsole();
                System.out.println("You have joined the group " + group.getName());

                this.render();
            }
            case CREATE_GROUP -> {
                if (currentUser.getRole().equals(Role.GENERAL)) {
                    ConsolClearer.clearConsole();

                    System.out.println("Access to create groups is denied");
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

                            ConsolClearer.clearConsole();
                            System.out.println(
                                    groupNameInput.getInput() + " group successfully created");
                            dataCorrect = true;
                        } catch (SignUpException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        promptBuilder = prompt.getPromptBuilder();
                        ConsolClearer.clearConsole();
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
                if (results.equals(null)) {
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
                    ConsolClearer.clearConsole();
                    this.render();
                }

                Result userResult = resultService.findByName(resultInput.getSelectedId());

                System.out.println(userResult.toString());

                actionsWithResult(userResult);
            }
            case VIEW_REPORTS -> {
                if (currentUser.getRole().equals(Role.GENERAL)) {
                    ConsolClearer.clearConsole();

                    System.out.println("Access to view reports is denied");
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
                    ConsolClearer.clearConsole();
                    this.render();
                }

                Report userReport = reportService.findByName(reportInput.getSelectedId());

                System.out.println(userReport.toString());

                actionsWithReport(userReport);
            }
            case EXIT -> {
            }
        }
    }

    enum TesterMenu {
        CREATE_TEST("Create test"),
        RUN_TEST("Run test"),
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

        TesterMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private void actionsWithResult(Result userResult) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("result-menu")
                .message("Result Menu  " + TesterMenu.USERNAME.getName() + currentUser.getUsername()
                        + " " + TesterMenu.ROLE.getName() + currentUser.getRole());

        listPromptBuilder.newItem(RENAME_RESULT.toString()).text(RENAME_RESULT.getName()).add();
        listPromptBuilder.newItem(DELETE_RESULT.toString()).text(DELETE_RESULT.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("result-menu");

        TesterMenu selectedItem = TesterMenu.valueOf(resultItem.getSelectedId());

        prompt = new ConsolePrompt();
        promptBuilder = prompt.getPromptBuilder();
        switch (selectedItem) {
            case RENAME_RESULT -> {
                promptBuilder.createInputPrompt()
                        .name("result-name")
                        .message("Type name for result: ")
                        .addPrompt();

                result = prompt.prompt(promptBuilder.build());

                var resultNameInput = (InputResult) result.get("result-name");

                ResultAddDto resultAddDto = new ResultAddDto(userResult.getId(),
                        resultNameInput.getInput(), userResult.getOwnerUsername(),
                        userResult.getMark(), userResult.getTestTitle(), userResult.getCreatedAt());
                resultService.remove(userResult);
                resultService.add(resultAddDto);
                jsonRepositoryFactory.commit();

                ConsolClearer.clearConsole();
                System.out.println("Set new name for result: " + resultNameInput.getInput());

                this.actionsWithResult(userResult);
            }

            case DELETE_RESULT -> {
                promptBuilder.createConfirmPromp()
                        .name("delete-result")
                        .message("Are you that you want delete this result?")
                        .defaultValue(ConfirmChoice.ConfirmationValue.NO)
                        .addPrompt();

                result = prompt.prompt(promptBuilder.build());

                var userChoice = (ConfirmResult) result.get("delete-result");

                if (userChoice.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                    resultService.remove(userResult);
                    jsonRepositoryFactory.commit();

                    ConsolClearer.clearConsole();
                    System.out.println("Result has been successfully deleted");
                    this.render();
                } else {
                    ConsolClearer.clearConsole();
                    actionsWithResult(userResult);
                }
            }

            case EXIT -> {
                ConsolClearer.clearConsole();
                this.render();
            }
        }
    }

    private void actionsWithReport(Report userReport) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();
        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("report-menu")
                .message("Report Menu  " + TesterMenu.USERNAME.getName() + currentUser.getUsername()
                        + " " + TesterMenu.ROLE.getName() + currentUser.getRole());

        listPromptBuilder.newItem(RENAME_REPORT.toString()).text(RENAME_REPORT.getName()).add();
        listPromptBuilder.newItem(DELETE_REPORT.toString()).text(DELETE_REPORT.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("report-menu");

        TesterMenu selectedItem = TesterMenu.valueOf(resultItem.getSelectedId());

        prompt = new ConsolePrompt();
        promptBuilder = prompt.getPromptBuilder();
        switch (selectedItem) {
            case RENAME_REPORT -> {
                promptBuilder.createInputPrompt()
                        .name("report-name")
                        .message("Type name for report: ")
                        .addPrompt();

                result = prompt.prompt(promptBuilder.build());

                var reportNameInput = (InputResult) result.get("report-name");

                ReportAddDto reportAddDto = new ReportAddDto(userReport.getId(),
                        reportNameInput.getInput(), userReport.getTestTitle(),
                        userReport.getGroupName(), userReport.getMinResult(),
                        userReport.getMaxResult(), userReport.getAverageResult(),
                        userReport.getCreatedAt());
                reportService.remove(userReport);
                reportService.add(reportAddDto);
                jsonRepositoryFactory.commit();

                ConsolClearer.clearConsole();
                System.out.println("Set new name for report: " + reportNameInput.getInput());

                actionsWithReport(userReport);
            }

            case DELETE_REPORT -> {
                promptBuilder.createConfirmPromp()
                        .name("delete-report")
                        .message("Are you that you want delete this report?")
                        .defaultValue(ConfirmChoice.ConfirmationValue.NO)
                        .addPrompt();

                result = prompt.prompt(promptBuilder.build());

                var userChoice = (ConfirmResult) result.get("delete-report");

                if (userChoice.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                    reportService.remove(userReport);
                    jsonRepositoryFactory.commit();

                    ConsolClearer.clearConsole();
                    System.out.println("Report has been successfully deleted");
                    actionsWithReport(userReport);
                } else {
                    ConsolClearer.clearConsole();
                    actionsWithReport(userReport);
                }
            }

            case EXIT -> {
                ConsolClearer.clearConsole();
                this.render();
            }
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
