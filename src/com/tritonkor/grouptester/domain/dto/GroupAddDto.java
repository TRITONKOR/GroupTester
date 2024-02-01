package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The GroupAddDto class represents a data transfer object for adding a group. It extends the Entity
 * class, providing identification through a UUID.
 */
public class GroupAddDto extends Entity {

    private String name;
    private Set<User> users;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new GroupAddDto with the specified parameters.
     *
     * @param id        the unique identifier for the group.
     * @param name      the name of the group.
     * @param users     the set of users belonging to the group.
     * @param createdAt the date and time when the group was created.
     */
    public GroupAddDto(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = ValidationUtil.validateName(name);
        this.users = users;
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    /**
     * Gets the name of the group.
     *
     * @return the name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the set of users belonging to the group.
     *
     * @return the set of users belonging to the group.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Gets the date and time when the group was created.
     *
     * @return the date and time when the group was created.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
