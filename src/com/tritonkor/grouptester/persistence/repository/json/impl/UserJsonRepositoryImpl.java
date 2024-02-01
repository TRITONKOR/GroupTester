package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the UserRepository interface using JSON for data storage. Extends the
 * GenericJsonRepository for common JSON repository functionality.
 */
public class UserJsonRepositoryImpl
        extends GenericJsonRepository<User>
        implements UserRepository {

    /**
     * Constructs a new UserJsonRepositoryImpl instance.
     *
     * @param gson The Gson instance for JSON serialization/deserialization.
     */
    public UserJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.USERS.getPath(), TypeToken
                .getParameterized(Set.class, User.class)
                .getType());
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to search for.
     * @return An optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return entities.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to search for.
     * @return An optional containing the user if found, otherwise empty.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return entities.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}
