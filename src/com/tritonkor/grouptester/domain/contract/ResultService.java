package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import java.util.Set;

/**
 * The ResultService interface defines the contract for result-related operations. It provides
 * methods for finding results by username, test title, or result name, adding new results, and
 * retrieving sets of results based on specific criteria.
 */
public interface ResultService extends Service<Result> {

    /**
     * Finds all results associated with a specific username.
     *
     * @param username The username for which to find results.
     * @return A set of Result objects representing the results associated with the given username.
     */
    Set<Result> findAllByUsername(String username);

    /**
     * Finds all results associated with a specific test title.
     *
     * @param testTitle The title of the test for which to find results.
     * @return A set of Result objects representing the results associated with the given test
     * title.
     */
    Set<Result> findAllByTestTitle(String testTitle);

    /**
     * Finds a result by its name.
     *
     * @param resultName The name of the result to be found.
     * @return The Result object representing the found result, or null if no result is found with
     * the given name.
     */
    Result findByName(String resultName);

    /**
     * Adds a new result based on the provided ResultAddDto.
     *
     * @param resultAddDto The ResultAddDto containing the details for creating a new result.
     * @return The Result object representing the newly added result.
     */
    Result add(ResultAddDto resultAddDto);
}
