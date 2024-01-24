package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilder;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilderBirthday;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilderEmail;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilderId;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilderPassword;
import com.tritonkor.grouptester.persistence.entity.impl.User.UserBuilderUsername;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Group extends Entity {

    private String name;
    private final LocalDate createdAt;
    private List<User> users;

    private Group(UUID id, String name, List<User> users, LocalDate createdAt) {
        super(id);
        this.name = Validation.validateText(name, errors, 10);
        this.users = users;
        this.createdAt = createdAt;
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

        GroupBuilderCreatedAt users(List<User> users);
    }

    public interface GroupBuilderCreatedAt {

        GroupBuilder createdAt(LocalDate createdAt);
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
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
