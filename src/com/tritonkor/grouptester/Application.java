package com.tritonkor.grouptester;

import static org.fusesource.jansi.Ansi.ansi;

import com.tritonkor.grouptester.appui.AuthView;
import com.tritonkor.grouptester.appui.MainMenuView;
import com.tritonkor.grouptester.appui.ReportMenuView;
import com.tritonkor.grouptester.appui.ResultMenuView;
import com.tritonkor.grouptester.appui.TestMenuView;
import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.impl.ServiceFactory;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import java.io.IOException;
import jline.TerminalFactory;
import org.fusesource.jansi.AnsiConsole;

/**
 * The {@code Application} class is the entry point for the GroupTester application.
 * It initializes necessary services and views to start the application's functionality.
 */
public final class Application {

    /** The JSON repository factory used to create repositories for data persistence. */
    public static RepositoryFactory jsonRepositoryFactory;

    /**
     * Initializes the GroupTester application by setting up services, views, and necessary dependencies.
     * This method also handles the installation of ANSI console features for better command-line interface rendering.
     */
    static void init() {
        // Initialization of the JSON repository factory and services
        jsonRepositoryFactory = RepositoryFactory
                .getRepositoryFactory(RepositoryFactory.JSON);
        ServiceFactory serviceFactory = ServiceFactory.getInstance(jsonRepositoryFactory);

        AuthService authService = serviceFactory.getAuthService();
        SignUpService signUpService = serviceFactory.getSignUpService();

        TestService testService = serviceFactory.getTestService();
        UserService userService = serviceFactory.getUserService();
        ResultService resultService = serviceFactory.getResultService();
        ReportService reportService = serviceFactory.getReportService();
        GroupService groupService = serviceFactory.getGroupService();

        //===
        // ANSI console setup for improved command-line interface rendering
        AnsiConsole.systemInstall();                                      // #1
        System.out.println(ansi().eraseScreen().render(""));
        System.out.print("\033[H\033[2J");
        System.out.flush();

        try {
            TestMenuView testMenuView = new TestMenuView(testService, resultService, groupService, userService, reportService);
            ResultMenuView resultMenuView = new ResultMenuView(testService, resultService, userService);
            ReportMenuView reportMenuView = new ReportMenuView(testService, reportService, userService);

            MainMenuView testView = new MainMenuView(testService, resultService, reportService, userService,
                    groupService, authService, testMenuView, resultMenuView, reportMenuView);

            AuthView authView = new AuthView(authService, signUpService, userService, testView);
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

        // JSON repository commit (must be included at the end of the main method)
        jsonRepositoryFactory.commit();
    }
}
