package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the ResultRepository interface using JSON for data storage. Extends the
 * GenericJsonRepository for common JSON repository functionality.
 */
public class ResultJsonRepositoryImpl
        extends GenericJsonRepository<Result>
        implements ResultRepository {

    /**
     * Constructs a new ResultJsonRepositoryImpl instance.
     *
     * @param gson The Gson instance for JSON serialization/deserialization.
     */
    public ResultJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.RESULTS.getPath(),
                TypeToken.getParameterized(Set.class, Result.class).getType());
    }

    /**
     * Finds a result by its name.
     *
     * @param resultName The name of the result to search for.
     * @return An optional containing the result if found, otherwise empty.
     */
    @Override
    public Optional<Result> findByName(String resultName) {
        return entities.stream().filter(r -> r.getResultTitle().equals(resultName)).findFirst();
    }

    /**
     * Finds all results associated with a specific username.
     *
     * @param username The username to filter results.
     * @return A set of results associated with the specified username.
     */
    @Override
    public Set<Result> findAllByUsername(String username) {
        return entities.stream().filter(r -> r.getOwnerUsername().equals(username))
                .collect(Collectors.toSet());
    }

    /**
     * Finds all results associated with a specific test title.
     *
     * @param testTitle The title of the test to filter results.
     * @return A set of results associated with the specified test title.
     */
    @Override
    public Set<Result> findAllByTestTitle(String testTitle) {
        return entities.stream().filter(r -> r.getTestTitle().equals(testTitle))
                .collect(Collectors.toSet());
    }
}
