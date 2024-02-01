package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.ResultMenuView.ResultMenu.RENAME_RESULT;
import static com.tritonkor.grouptester.appui.ResultMenuView.ResultMenu.DELETE_RESULT;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.ROLE;
import static com.tritonkor.grouptester.appui.MainMenuView.MainMenu.USERNAME;
import static com.tritonkor.grouptester.appui.ResultMenuView.ResultMenu.EXIT;

import com.tritonkor.grouptester.appui.MainMenuView.MainMenu;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.elements.ConfirmChoice;
import de.codeshelf.consoleui.prompt.ConfirmResult;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;

/**
 * The ResultMenuView class provides a console-based user interface for interacting with results in
 * a group testing application. It allows users to rename or delete results and return to the main
 * menu. The class implements the Renderable interface for rendering the menu and handling user
 * input.
 */
public class ResultMenuView implements Renderable {

    private final TestService testService;
    private final ResultService resultService;
    private final UserService userService;

    private MainMenuView mainMenuView;

    private User currentUser;
    private Result currentResult;

    /**
     * Constructs a new ResultMenuView with the specified services.
     *
     * @param testService   The service for managing tests.
     * @param resultService The service for managing results.
     * @param userService   The service for managing users.
     */
    public ResultMenuView(TestService testService, ResultService resultService,
            UserService userService) {
        this.testService = testService;
        this.resultService = resultService;
        this.userService = userService;
    }

    /**
     * Renders the result menu, allowing the user to choose actions such as renaming or deleting
     * results.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("result-menu")
                .message("Result Menu  " + USERNAME.getName() + currentUser.getUsername()
                        + " " + ROLE.getName() + currentUser.getRole());

        listPromptBuilder.newItem(RENAME_RESULT.toString()).text(RENAME_RESULT.getName()).add();
        listPromptBuilder.newItem(DELETE_RESULT.toString()).text(DELETE_RESULT.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(MainMenu.EXIT.getName()).add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("result-menu");

        ResultMenu selectedItem = ResultMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    /**
     * Processes the selected option from the result menu and performs the corresponding actions.
     *
     * @param selectedItem The selected option from the result menu.
     * @throws IOException If an I/O error occurs.
     */
    private void process(ResultMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {
            case RENAME_RESULT -> {
                promptBuilder.createInputPrompt()
                        .name("result-name")
                        .message("Type name for result: ")
                        .addPrompt();
                InputResult testNameInput = null;
                do {
                    try {
                        var result = prompt.prompt(promptBuilder.build());

                        testNameInput = (InputResult) result.get("result-name");

                        ResultAddDto resultAddDto = new ResultAddDto(currentResult.getId(),
                                testNameInput.getInput(), currentResult.getOwnerUsername(),
                                currentResult.getMark(), currentResult.getTestTitle(),
                                currentResult.getCreatedAt());
                        resultService.remove(currentResult);
                        resultService.add(resultAddDto);
                        jsonRepositoryFactory.commit();
                        break;
                    } catch (EntityArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                ConsolItems.clearConsole();
                System.out.println("Set new name for result: " + testNameInput.getInput());

                this.render();
            }

            case DELETE_RESULT -> {
                promptBuilder.createConfirmPromp()
                        .name("delete-result")
                        .message("Are you that you want delete this result?")
                        .defaultValue(ConfirmChoice.ConfirmationValue.NO)
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());

                var userChoice = (ConfirmResult) result.get("delete-result");

                if (userChoice.getConfirmed() == ConfirmChoice.ConfirmationValue.YES) {
                    resultService.remove(currentResult);
                    jsonRepositoryFactory.commit();

                    ConsolItems.clearConsole();
                    System.out.println("Result has been successfully deleted✅");
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

    /**
     * Enumeration representing the available options in the result menu.
     */
    enum ResultMenu {
        RENAME_RESULT("Rename result\uD83C\uDFF7\uFE0F"),
        DELETE_RESULT("Delete result\uD83D\uDDD1\uFE0F"),
        EXIT("Exit\uD83D\uDEAA");

        private final String name;

        ResultMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Sets the current user for the ResultMenuView.
     *
     * @param user The current user.
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Sets the current result for the ResultMenuView.
     *
     * @param result The current result.
     */
    public void setCurrentResult(Result result) {
        this.currentResult = result;
    }

    /**
     * Sets the main menu view for the ResultMenuView.
     *
     * @param mainMenuView The main menu view.
     */
    public void setMainMenuView(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
    }
}
