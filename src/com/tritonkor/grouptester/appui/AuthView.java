package com.tritonkor.grouptester.appui;

import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.EXIT;
import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.SIGN_IN;
import static com.tritonkor.grouptester.appui.AuthView.AuthMenu.SIGN_UP;

import com.tritonkor.grouptester.domain.Validation;
import com.tritonkor.grouptester.domain.contract.AuthService;

import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import de.codeshelf.consoleui.prompt.ConsolePrompt;
import de.codeshelf.consoleui.prompt.InputResult;
import de.codeshelf.consoleui.prompt.ListResult;
import de.codeshelf.consoleui.prompt.builder.PromptBuilder;
import java.io.IOException;
import java.util.UUID;

public class AuthView implements Renderable {

    private final AuthService authService;

    private final SignUpService signUpService;

    public AuthView(AuthService authService, SignUpService signUpService) {
        this.authService = authService;
        this.signUpService = signUpService;
    }

    private void process(AuthMenu selectedItem) throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        switch (selectedItem) {
            case SIGN_IN -> {
                promptBuilder.createInputPrompt()
                        .name("username")
                        .message("Type your login: ")
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("password")
                        .message("Type your password: ")
                        .mask('*')
                        .addPrompt();

                var result = prompt.prompt(promptBuilder.build());
                var usernameInput = (InputResult) result.get("username");
                var passwordInput = (InputResult) result.get("password");

                try {
                    boolean authenticate = authService.authenticate(
                            usernameInput.getInput(),
                            passwordInput.getInput()
                    );
                    System.out.printf("%s %n", authenticate);
                } catch (AuthException e) {
                    System.err.println(e.getMessage());
                }


            }
            case SIGN_UP -> {
                boolean dataCorrect = true;

                do {
                    promptBuilder.createInputPrompt()
                            .name("username")
                            .message("Type your login: ")
                            .addPrompt();
                    promptBuilder.createInputPrompt()
                            .name("password")
                            .message(
                                    "Type your password(capital letters, numbers, and special characters must be present): ")
                            .mask('*')
                            .addPrompt();
                    promptBuilder.createInputPrompt()
                            .name("email")
                            .message("Type your email: ")
                            .addPrompt();
                    promptBuilder.createInputPrompt()
                            .name("birthday")
                            .message("Type your birthday(example: 1977-4-11): ")
                            .addPrompt();

                    try {

                        var result = prompt.prompt(promptBuilder.build());

                        var usernameInput = (InputResult) result.get("username");

                        var passwordInput = (InputResult) result.get("password");
                        String validatedPassword = Validation.validatePassword(
                                passwordInput.getInput());

                        var emailInput = (InputResult) result.get("email");
                        var birthdayInput = (InputResult) result.get("birthday");

                        prompt = new ConsolePrompt();
                        promptBuilder = prompt.getPromptBuilder();

                        System.out.println(
                                "Please little wait, we are sending list on your email with verification code");
                        String verificationCode = signUpService.generateAndSendVerificationCode(
                                emailInput.getInput());

                        promptBuilder.createInputPrompt()
                                .name("verify code")
                                .message("Type your verification code: ")
                                .addPrompt();
                        result = prompt.prompt(promptBuilder.build());
                        var verifyCodeInput = (InputResult) result.get("verify code");
                        try {
                            UserAddDto userAddDto = new UserAddDto(UUID.randomUUID(),
                                    usernameInput.getInput(), validatedPassword,
                                    emailInput.getInput(), birthdayInput.getInput());
                            signUpService.signUp(userAddDto, verifyCodeInput.getInput(),
                                    verificationCode);
                            System.out.printf("%s %n", "Account created");

                            clearConsole();

                            dataCorrect = true;
                        } catch (AuthException e) {
                            System.err.println(e.getMessage());
                        }
                    } catch (EntityArgumentException e) {
                        clearConsole();

                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);
            }
            case EXIT -> {
            }
            default -> {

            }
        }
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        promptBuilder.createListPrompt()
                .name("auth-menu")
                .message("Group Tester")
                .newItem(SIGN_IN.toString()).text(SIGN_IN.getName()).add()
                .newItem(SIGN_UP.toString()).text(SIGN_UP.getName()).add()
                .newItem(EXIT.toString()).text(EXIT.getName()).add()
                .addPrompt();

        var result = prompt.prompt(promptBuilder.build());
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
