package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of the GroupRepository interface using JSON for data storage. Extends the
 * GenericJsonRepository for common JSON repository functionality.
 */
public class GroupJsonRepositoryImpl
        extends GenericJsonRepository<Group>
        implements GroupRepository {

    /**
     * Constructs a new GroupJsonRepositoryImpl instance.
     *
     * @param gson The Gson instance for JSON serialization/deserialization.
     */
    public GroupJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.GROUPS.getPath(),
                TypeToken.getParameterized(Set.class, Group.class).getType());
    }

    /**
     * Finds a group by the specified user.
     *
     * @param user The user to search for within groups.
     * @return An optional containing the group if found, otherwise empty.
     */
    @Override
    public Optional<Group> findByUser(User user) {
        return entities.stream()
                .filter(group -> group.getUsers().contains(user))
                .findFirst();
    }

    /**
     * Finds a group by its name.
     *
     * @param name The name of the group to search for.
     * @return An optional containing the group if found, otherwise empty.
     */
    @Override
    public Optional<Group> findByName(String name) {
        return entities.stream().filter(g -> g.getName().equals(name)).findFirst();
    }
}
