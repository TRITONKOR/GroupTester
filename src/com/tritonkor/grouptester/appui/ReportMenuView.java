package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.ReportMenuView.ReportMenu.DELETE_REPORT;
import static com.tritonkor.grouptester.appui.ReportMenuView.ReportMenu.RENAME_REPORT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.ROLE;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.USERNAME;
import static com.tritonkor.grouptester.appui.ReportMenuView.ReportMenu.EXIT;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;

public class ReportMenuView implements Renderable{

    private final TestService testService;
    private final ReportService reportService;
    private final UserService userService;

    private MainMenuView mainMenuView;

    private User currentUser;
    private Report currentReport;

    public ReportMenuView(TestService testService, ReportService reportService,
            UserService userService) {
        this.testService = testService;
        this.reportService = reportService;
        this.userService = userService;
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("report-menu")
                .message("Report Menu  " + USERNAME.getName() + currentUser.getUsername()
                        + " " + ROLE.getName() + currentUser.getRole());

        listPromptBuilder.newItem(RENAME_REPORT.toString()).text(RENAME_REPORT.getName()).add();
        listPromptBuilder.newItem(DELETE_REPORT.toString()).text(DELETE_REPORT.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("report-menu");

        ReportMenu selectedItem = ReportMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    private void process(ReportMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {
            case RENAME_REPORT -> {
                promptBuilder.createInputPrompt()
                        .name("report-name")
                        .message("Type name for report: ")
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());

                var reportNameInput = (InputResult) result.get("report-name");

                ReportAddDto reportAddDto = new ReportAddDto(currentReport.getId(),
                        reportNameInput.getInput(), currentReport.getTestTitle(),
                        currentReport.getGroupName(), currentReport.getMinResult(),
                        currentReport.getMaxResult(), currentReport.getAverageResult(),
                        currentReport.getUsersResults(),
                        currentReport.getCreatedAt());
                reportService.remove(currentReport);
                reportService.add(reportAddDto);
                jsonRepositoryFactory.commit();

                ConsolItems.clearConsole();
                System.out.println("Set new name for report: " + reportNameInput.getInput());

                this.render();
            }

            case DELETE_REPORT -> {
                promptBuilder.createConfirmPromp()
                        .name("delete-report")
                        .message("Are you that you want delete this report?")
                        .defaultValue(ConfirmChoice.ConfirmationValue.NO)
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());

                var userChoice = (ConfirmResult) result.get("delete-report");

                if (userChoice.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                    reportService.remove(currentReport);
                    jsonRepositoryFactory.commit();

                    ConsolItems.clearConsole();
                    System.out.println("Report has been successfully deleted");
                    this.render();
                } else {
                    ConsolItems.clearConsole();
                    this.render();
                }
            }

            case EXIT -> {
                ConsolItems.clearConsole();
                mainMenuView.render();
            }
        }
    }

    enum ReportMenu {
        RENAME_REPORT("Rename report"),
        DELETE_REPORT("Delete report"),
        EXIT("Exit");

        private final String name;

        ReportMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void setCurrentReport(Report report) {
        this.currentReport = report;
    }

    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }
}
