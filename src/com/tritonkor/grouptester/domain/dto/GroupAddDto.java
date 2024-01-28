package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class GroupAddDto extends Entity {

    private String name;
    private Set<User> users;
    private final LocalDateTime createdAt;

    public GroupAddDto(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = ValidationUtil.validateName(name);
        this.users = users;
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    public String getName() {
        return name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
