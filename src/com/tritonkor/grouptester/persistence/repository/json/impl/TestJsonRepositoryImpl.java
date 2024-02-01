package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the TestRepository interface using JSON for data storage.
 * Extends the GenericJsonRepository for common JSON repository functionality.
 */
public class TestJsonRepositoryImpl
        extends GenericJsonRepository<Test>
        implements TestRepository {

    /**
     * Constructs a new TestJsonRepositoryImpl instance.
     *
     * @param gson The Gson instance for JSON serialization/deserialization.
     */
    public TestJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.TESTS.getPath(), TypeToken
                .getParameterized(Set.class, Test.class)
                .getType());
    }

    /**
     * Finds a test by its title.
     *
     * @param title The title of the test to search for.
     * @return An optional containing the test if found, otherwise empty.
     */
    @Override
    public Optional<Test> findByTitle(String title) {
        return entities.stream().filter(t -> t.getTitle().equals(title)).findFirst();
    }
}
