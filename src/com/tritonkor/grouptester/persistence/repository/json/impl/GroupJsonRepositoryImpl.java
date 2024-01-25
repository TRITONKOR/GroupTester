package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import java.util.Optional;
import java.util.Set;

public class GroupJsonRepositoryImpl
        extends GenericJsonRepository<Group>
        implements GroupRepository {

    public GroupJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.GROUPS.getPath(),
                TypeToken.getParameterized(Set.class, Group.class).getType());
    }

    @Override
    public Optional<Group> findByName(String name) {
        return entities.stream().filter(g -> g.getName().equals(name)).findFirst();
    }
}
