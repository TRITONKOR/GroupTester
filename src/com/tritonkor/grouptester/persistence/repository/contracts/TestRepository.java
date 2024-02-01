package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;

/**
 * The {@code TestRepository} interface provides methods for interacting with the repository of
 * {@link Test} entities.
 */
public interface TestRepository extends Repository<Test> {

    /**
     * Finds a test by its title.
     *
     * @param title The title of the test to find.
     * @return An optional containing the test if found, or empty if not found.
     */
    Optional<Test> findByTitle(String title);
}
