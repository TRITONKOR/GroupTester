package com.tritonkor.grouptester;

import static org.fusesource.jansi.Ansi.ansi;

import com.tritonkor.grouptester.appui.AuthView;
import com.tritonkor.grouptester.appui.TestView;
import com.tritonkor.grouptester.domain.Generator;
import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.impl.ServiceFactory;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;
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
        System.out.print("\033[H\033[2J");
        System.out.flush();

        AnsiConsole.systemInstall();                                      // #1
        System.out.println(ansi().eraseScreen().render("Group Test AuthMenu:"));

        try {
            TestView testView = new TestView(testService, resultService, reportService, groupService, authService);
            AuthView authView = new AuthView(authService, signUpService, userService, testView);
            authView.render();
            System.out.println("Peremoha");
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
