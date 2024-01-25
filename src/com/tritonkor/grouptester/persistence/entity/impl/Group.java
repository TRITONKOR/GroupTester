package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Group extends Entity {

    private String name;
    private final LocalDateTime createdAt;
    private Set<User> users;

    private Group(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = Validation.validateText(name, errors, 10);
        this.users = users;
        this.createdAt = Validation.validateDateTime(createdAt, errors);
    }

    public static GroupBuilderId builder() {
        return id -> name -> users -> createdAt -> () -> new Group(id, name, users, createdAt);
    }

    public interface GroupBuilderId {

        GroupBuilderName id(UUID id);
    }

    public interface GroupBuilderName {

        GroupBuilderUsers name(String name);
    }

    public interface GroupBuilderUsers {

        GroupBuilderCreatedAt users(Set<User> users);
    }

    public interface GroupBuilderCreatedAt {

        GroupBuilder createdAt(LocalDateTime createdAt);
    }

    public interface GroupBuilder {

        Group build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", users=" + users +
                '}';
    }
}
