package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.util.Optional;
import java.util.Set;

public class UserJsonRepositoryImpl
        extends GenericJsonRepository<User>
        implements UserRepository {

    public UserJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.USERS.getPath(), TypeToken
                .getParameterized(Set.class, User.class)
                .getType());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return entities.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return entities.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }
}