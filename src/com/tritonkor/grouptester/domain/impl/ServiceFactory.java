package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.contract.GroupService;
import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.contract.SignUpService;
import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.exception.DependencyException;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;

public final class ServiceFactory {

    private static volatile ServiceFactory INSTANCE;
    private final AuthService authService;

    private final SignUpService signUpService;

    private final TestService testService;

    private final UserService userService;

    private final ResultService resultService;

    private final ReportService reportService;

    private final GroupService groupService;

    private final RepositoryFactory repositoryFactory;

    public ServiceFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;

        testService = new TestServiceImpl(repositoryFactory.getTestRepository());
        resultService = new ResultServiceImpl(repositoryFactory.getResultRepository());
        reportService = new ReportServiceImpl(repositoryFactory.getReportRepository());
        userService = new UserServiceImpl(repositoryFactory.getUserRepository());
        groupService = new GroupServiceImpl(repositoryFactory.getGroupRepository());
        signUpService = new SignUpServiceImpl(userService);
        authService = new AuthServiceImpl(repositoryFactory.getUserRepository());
    }

    /**
     * Використовувати, лише якщо впевнені, що існує об'єкт RepositoryFactory.
     *
     * @return екземпляр типу ServiceFactory
     */
    public static ServiceFactory getInstance() {
        if (INSTANCE.repositoryFactory != null) {
            return INSTANCE;
        } else {
            throw new DependencyException(
                    "Ви забули створити обєкт RepositoryFactory, перед використанням ServiceFactory.");
        }
    }

    public static ServiceFactory getInstance(RepositoryFactory repositoryFactory) {
        if (INSTANCE == null) {
            synchronized (ServiceFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ServiceFactory(repositoryFactory);
                }
            }
        }

        return INSTANCE;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public SignUpService getSignUpService() {
        return signUpService;
    }

    public TestService getTestService() {
        return testService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ResultService getResultService() {
        return resultService;
    }

    public ReportService getReportService() {
        return reportService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }
}
