package com.tritonkor.grouptester;

import static org.fusesource.jansi.Ansi.ansi;

import com.tritonkor.grouptester.appui.AuthView;
import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.impl.ServiceFactory;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import java.io.IOException;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

final class Application {

    static void init() {
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);
        AuthService authService = serviceFactory.getAuthService();
        SignUpService signUpService = serviceFactory.getSignUpService();

        //===
        System.out.print("\033[H\033[2J");
        System.out.flush();

        AnsiConsole.systemInstall();                                      // #1
        System.out.println(ansi().eraseScreen().render("Simple list example:"));

        try {
            AuthView authView = new AuthView(authService, signUpService);
            authView.render();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                TerminalFactory.get().restore();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //===

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }
}
