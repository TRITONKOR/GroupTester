package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class TestJsonRepositoryImpl
        extends GenericJsonRepository<Test>
        implements TestRepository {

    public TestJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.TESTS.getPath(), TypeToken
                .getParameterized(Set.class, Test.class)
                .getType());
    }

    @Override
    public Optional<Test> findByTitle(String title) {
        return entities.stream().filter(t -> t.getTitle().equals(title)).findFirst();
    }
}
