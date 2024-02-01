package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;

/**
 * The {@code UserRepository} interface provides methods for interacting with the repository of
 * {@link User} entities.
 */
public interface UserRepository extends Repository<User> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An optional containing the user if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An optional containing the user if found, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}
