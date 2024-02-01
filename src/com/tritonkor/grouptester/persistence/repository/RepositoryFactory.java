package com.tritonkor.grouptester.persistence.repository;

import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import com.tritonkor.grouptester.persistence.repository.json.impl.JsonRepositoryFactory;

/**
 * The abstract class {@code RepositoryFactory} serves as a factory for creating different types of
 * repositories.
 */
public abstract class RepositoryFactory {

    /**
     * Constant representing the JSON repository factory.
     */
    public static final int JSON = 1;

    /**
     * Retrieves an instance of the repository factory based on the provided type.
     *
     * @param whichFactory The type of factory to be instantiated.
     * @return An instance of the repository factory.
     * @throws IllegalArgumentException If an invalid factory type is provided.
     */
    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        return switch (whichFactory) {
            case JSON -> JsonRepositoryFactory.getInstance();
            default -> throw new IllegalArgumentException(
                    "Error when selecting a repository factory.");
        };
    }

    /**
     * Retrieves the user repository.
     *
     * @return The user repository.
     */
    public abstract UserRepository getUserRepository();

    /**
     * Retrieves the group repository.
     *
     * @return The group repository.
     */
    public abstract GroupRepository getGroupRepository();

    /**
     * Retrieves the test repository.
     *
     * @return The test repository.
     */
    public abstract TestRepository getTestRepository();

    /**
     * Retrieves the result repository.
     *
     * @return The result repository.
     */
    public abstract ResultRepository getResultRepository();

    /**
     * Retrieves the report repository.
     *
     * @return The report repository.
     */
    public abstract ReportRepository getReportRepository();

    /**
     * Commits any changes made to the repositories.
     */
    public abstract void commit();
}
