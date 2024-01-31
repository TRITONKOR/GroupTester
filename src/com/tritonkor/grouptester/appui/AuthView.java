package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.Application.jsonRepositoryFactory;
import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.EXIT;
import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.SIGN_IN;
import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.SIGN_UP;

import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.ListPromptBuilder;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.UUID;

public class AuthView implements Renderable {

    private final AuthService authService;

    private final SignUpService signUpService;

    private final UserService userService;

    private final MainMenuView testView;

    public AuthView(AuthService authService, SignUpService signUpService, UserService userService,
            MainMenuView testView) {
        this.authService = authService;
        this.signUpService = signUpService;
        this.userService = userService;

        this.testView = testView;
    }

    private void process(AuthMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {
            case SIGN_IN -> {
                boolean dataCorrect = true;

                do {
                    try {
                        promptBuilder.createInputPrompt()
                                .name("username")
                                .message("Type your login\uD83D\uDC64: ")
                                .addPrompt();
                        promptBuilder.createInputPrompt()
                                .name("password")
                                .message("Type your password\uD83D\uDD12: ")
                                .mask('*')
                                .addPrompt();

                        var result = prompt.prompt(promptBuilder.build());
                        var usernameInput = (InputResult) result.get("username");
                        var passwordInput = (InputResult) result.get("password");

                        authService.authenticate(usernameInput.getInput(),
                                passwordInput.getInput());

                        dataCorrect = true;
                    } catch (AuthException e) {
                        dataCorrect = false;

                        promptBuilder = new PromptBuilder();

                        ConsolItems.clearConsole();

                        promptBuilder.createListPrompt().name("user-choice")
                                .message("Login or password wrong❌  Wanna try again?")
                                .newItem("try-again").text("Try again\uD83D\uDD01").add()
                                .newItem("back").text("Back\uD83D\uDD19").add().addPrompt();

                        var result = prompt.prompt(promptBuilder.build());

                        var userChoice = (ListResult) result.get("user-choice");

                        prompt = new ConsolePrompt();
                        promptBuilder = new PromptBuilder();
                        ConsolItems.clearConsole();

                        if (userChoice.getSelectedId().equals("back")) {
                            this.render();
                        }
                    }
                } while (!dataCorrect);

                testView.render();
            }
            case SIGN_UP -> {
                boolean dataCorrect = true;

                promptBuilder.createInputPrompt()
                        .name("username")
                        .message("Type your login\uD83D\uDC64: ")
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("password")
                        .message(
                                "Type your password\uD83D\uDD12(capital letters, numbers): ")
                        .mask('*')
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("email")
                        .message("Type your email\uD83D\uDCE7: ")
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("birthday")
                        .message("Type your birthday\uD83C\uDF82(example: 1977-4-11): ")
                        .addPrompt();

                do {
                    try {

                        var result = prompt.prompt(promptBuilder.build());

                        var usernameInput = (InputResult) result.get("username");

                        var passwordInput = (InputResult) result.get("password");

                        var emailInput = (InputResult) result.get("email");

                        var birthdayInput = (InputResult) result.get("birthday");

                        UserAddDto userAddDto = new UserAddDto(UUID.randomUUID(),
                                usernameInput.getInput(), passwordInput.getInput(),
                                emailInput.getInput(), birthdayInput.getInput());

                        prompt = new ConsolePrompt();
                        promptBuilder = prompt.getPromptBuilder();

                        System.out.println(
                                "Please little wait\uD83D\uDD53, we are sending list on your email with verification code\uD83D\uDEE0\uFE0F");
                        //String verificationCode = signUpService.generateAndSendVerificationCode(emailInput.getInput());

                        promptBuilder.createInputPrompt()
                                .name("verify code")
                                .message("Type your verification code\uD83D\uDD22: ")
                                .addPrompt();

                        result = prompt.prompt(promptBuilder.build());
                        var verifyCodeInput = (InputResult) result.get("verify code");
                        try {
                            signUpService.signUp(userAddDto, verifyCodeInput.getInput(),
                                    "2");
                            jsonRepositoryFactory.commit();
                            authService.authenticate(userAddDto.username(),
                                    userAddDto.rawPassword());

                            dataCorrect = true;
                        } catch (AuthException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        dataCorrect = false;

                        ConsolItems.clearConsole();
                        System.out.println(e.getMessage());
                    }
                } while (!dataCorrect);

                ConsolItems.clearConsole();
                testView.render();
            }
            case EXIT -> {
                prompt = new ConsolePrompt();
                promptBuilder = prompt.getPromptBuilder();
                ConsolItems.clearConsole();
                System.out.println("Good bye, botik \uD83E\uDD7A\uD83D\uDC49\uD83D\uDC48");
            }
        }
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ConsolItems.clearConsole();

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("auth-menu")
                .message("Group Tester\uD83D\uDC77");

        listPromptBuilder.newItem(SIGN_IN.toString()).text(SIGN_IN.getName() + "\uD83E\uDEAA")
                .add();
        listPromptBuilder.newItem(SIGN_UP.toString()).text(SIGN_UP.getName() + "\uD83D\uDD11")
                .add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName() + "\uD83D\uDD90\uFE0F")
                .add();

        var result = prompt.prompt(listPromptBuilder.addPrompt().build());
        ListResult resultItem = (ListResult) result.get("auth-menu");

        AuthMenu selectedItem = AuthMenu.valueOf(resultItem.getSelectedId());
        process(selectedItem);
    }

    enum AuthMenu {
        SIGN_IN("Authorization"),
        SIGN_UP("Create an account"),
        EXIT("Exit");

        private final String name;

        AuthMenu(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


}
