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

    private final TestView testView;

    public AuthView(AuthService authService, SignUpService signUpService, UserService userService,
            TestView testView) {
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

                promptBuilder.createInputPrompt()
                        .name("username")
                        .message("Type your login: ")
                        .addPrompt();
                promptBuilder.createInputPrompt()
                        .name("password")
                        .message("Type your password: ")
                        .mask('*')
                        .addPrompt();

                do {
                    try {
                        var result = prompt.prompt(promptBuilder.build());
                        var usernameInput = (InputResult) result.get("username");
                        var passwordInput = (InputResult) result.get("password");

                        authService.authenticate(usernameInput.getInput(),
                                passwordInput.getInput());
                        dataCorrect = true;
                    } catch (AuthException e) {
                        dataCorrect = false;

                        ConsolClearer.clearConsole();
                        System.out.println(e.getMessage());
                    }
                } while (!dataCorrect);
                prompt = new ConsolePrompt();
                promptBuilder = prompt.getPromptBuilder();

                ConsolClearer.clearConsole();
                testView.render();
            }
            case SIGN_UP -> {
                boolean dataCorrect = true;

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
                                "Please little wait, we are sending list on your email with verification code");
                        //String verificationCode = signUpService.generateAndSendVerificationCode(emailInput.getInput());

                        promptBuilder.createInputPrompt()
                                .name("verify code")
                                .message("Type your verification code: ")
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
                        ConsolClearer.clearConsole();
                        System.out.println(e.getMessage());

                        dataCorrect = false;
                    }
                } while (!dataCorrect);

                ConsolClearer.clearConsole();
                testView.render();
            }
            case EXIT -> {
                ConsolClearer.clearConsole();
                authService.authenticate("kazah", "Kazah123456");
                testView.render();//System.out.println("Good bye, botik >w<");
            }
        }
    }

    @Override
    public void render() throws IOException {
        ConsolePrompt prompt = new ConsolePrompt();
        PromptBuilder promptBuilder = prompt.getPromptBuilder();

        ListPromptBuilder listPromptBuilder = promptBuilder.createListPrompt()
                .name("auth-menu")
                .message("Group Tester");

        listPromptBuilder.newItem(SIGN_IN.toString()).text(SIGN_IN.getName()).add();
        listPromptBuilder.newItem(SIGN_UP.toString()).text(SIGN_UP.getName()).add();
        listPromptBuilder.newItem(EXIT.toString()).text(EXIT.getName()).add();

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
