package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.appui.TestView.TesterMenu.CREATE_GROUP;
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
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
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
import de.codeshelf.consoleui.prompt.builder.InputValueBuilder;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.security.Permission;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

public class TestView implements Renderable {

    private final TestService testService;
    private final ResultService resultService;
    private final GroupService groupService;
    private final ReportService reportService;
    private final AuthService authService;

    private User currentUser;

    private Permission permissions;

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

                            ConsolClearer.clearConsole();
                            System.out.println(groupNameInput.getInput() + " group successfully created");
                            dataCorrect = true;
                        } catch (AuthException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        prompt = new ConsolePrompt();
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
                for (Result result : results) {
                    listPromptBuilder.newItem(result.getResultTitle()).text(result.getResultTitle())
                            .add();
                }
                listPromptBuilder.newItem("back").text("Back to menu").add();

                var result = prompt.prompt(listPromptBuilder.addPrompt().build());
                ListResult resultInput = (ListResult) result.get("results-list");

                if (resultInput.getSelectedId().equals("back")) {
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
                    this.render();
                }

                Report userReport = reportService.findByName(reportInput.getSelectedId());

                System.out.println(userReport.toString());

                listPromptBuilder = promptBuilder.createListPrompt();
                actionsWithReport(userReport);
            }
            case EXIT -> {
            }
        }
    }

    enum TesterMenu {
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

                userResult.setResultTitle(resultNameInput.getInput());

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

                    ConsolClearer.clearConsole();
                    System.out.println("Result has been successfully deleted");
                    actionsWithResult(userResult);
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

                var resultNameInput = (InputResult) result.get("report-name");

                userReport.setReportTitle(resultNameInput.getInput());

                ConsolClearer.clearConsole();
                System.out.println("Set new name for report: " + resultNameInput.getInput());

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
