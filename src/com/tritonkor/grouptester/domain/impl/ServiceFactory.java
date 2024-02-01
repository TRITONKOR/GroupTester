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

/**
 * The ServiceFactory class is responsible for creating and providing instances of various services
 * in the application, such as authentication, sign-up, test, user, result, report, and group
 * services. It ensures that there is a single instance of the factory and services throughout the
 * application.
 */
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

    /**
     * Constructs a new ServiceFactory with the specified RepositoryFactory.
     *
     * @param repositoryFactory the repository factory used for creating repositories.
     */
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
     * Returns the singleton instance of the ServiceFactory. This method should only be used when it
     * is certain that a RepositoryFactory object exists.
     *
     * @return the singleton instance of the ServiceFactory.
     * @throws DependencyException if a RepositoryFactory object is not created before using the
     *                             ServiceFactory.
     */
    public static ServiceFactory getInstance() {
        if (INSTANCE.repositoryFactory != null) {
            return INSTANCE;
        } else {
            throw new DependencyException(
                    "You forgot to create the RepositoryFactory object before using the ServiceFactory.");
        }
    }

    /**
     * Returns the singleton instance of the ServiceFactory, creating it if it doesn't exist.
     *
     * @param repositoryFactory the repository factory used for creating repositories.
     * @return the singleton instance of the ServiceFactory.
     */
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

    /**
     * Gets the AuthService instance.
     *
     * @return the AuthService instance.
     */
    public AuthService getAuthService() {
        return authService;
    }

    /**
     * Gets the SignUpService instance.
     *
     * @return the SignUpService instance.
     */
    public SignUpService getSignUpService() {
        return signUpService;
    }

    /**
     * Gets the TestService instance.
     *
     * @return the TestService instance.
     */
    public TestService getTestService() {
        return testService;
    }

    /**
     * Gets the UserService instance.
     *
     * @return the UserService instance.
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Gets the ResultService instance.
     *
     * @return the ResultService instance.
     */
    public ResultService getResultService() {
        return resultService;
    }

    /**
     * Gets the ReportService instance.
     *
     * @return the ReportService instance.
     */
    public ReportService getReportService() {
        return reportService;
    }

    /**
     * Gets the GroupService instance.
     *
     * @return the GroupService instance.
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * Gets the RepositoryFactory instance.
     *
     * @return the RepositoryFactory instance.
     */
    public RepositoryFactory getRepositoryFactory() {
        return repositoryFactory;
    }
}
