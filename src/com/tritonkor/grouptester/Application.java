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

public final class Application {

    public static RepositoryFactory jsonRepositoryFactory;

    static void init() {
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

        // Цей рядок, має бути обовязково в кінці метода main!!!!
        jsonRepositoryFactory.commit();
    }
}
