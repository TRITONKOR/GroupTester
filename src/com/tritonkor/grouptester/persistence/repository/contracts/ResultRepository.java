package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code ResultRepository} interface provides methods for interacting with the repository of
 * {@link Result} entities.
 */
public interface ResultRepository extends Repository<Result> {

    /**
     * Finds all results associated with a specific user.
     *
     * @param username The username of the user for which to find results.
     * @return A set of results associated with the specified user.
     */
    Set<Result> findAllByUsername(String username);

    /**
     * Finds all results associated with a specific test.
     *
     * @param testTitle The title of the test for which to find results.
     * @return A set of results associated with the specified test.
     */
    Set<Result> findAllByTestTitle(String testTitle);

    /**
     * Finds a result by its name.
     *
     * @param resultName The name of the result to find.
     * @return An optional containing the result if found, or empty if not found.
     */
    Optional<Result> findByName(String resultName);
}
