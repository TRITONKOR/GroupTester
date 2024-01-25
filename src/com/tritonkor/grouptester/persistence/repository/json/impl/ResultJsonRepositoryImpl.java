package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import java.util.Set;
import java.util.stream.Collectors;

public class ResultJsonRepositoryImpl
        extends GenericJsonRepository<Result>
        implements ResultRepository {

    public ResultJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.RESULTS.getPath(),
                TypeToken.getParameterized(Set.class, Result.class).getType());
    }

    @Override
    public Set<Result> findAllByUser(User user) {
        return entities.stream().filter(r -> r.getOwner().equals(user)).collect(Collectors.toSet());
    }

    @Override
    public Set<Result> findAllByTest(String testTitle) {
        return entities.stream().filter(r -> r.getTestTitle().equals(testTitle)).collect(Collectors.toSet());
    }
}
