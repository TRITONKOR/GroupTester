package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.domain.impl.TestServiceImpl;
import com.tritonkor.grouptester.domain.observer.Subject;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.domain.observer.Observer;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Group extends Entity implements Subject {

    private String name;
    private Set<User> users;
    private final LocalDateTime createdAt;

    private Group(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = name;
        this.users = new HashSet<>();
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

        GroupBuilderCreatedAt users(Set<User> users);
    }

    public interface GroupBuilderCreatedAt {

        GroupBuilder createdAt(LocalDateTime createdAt);
    }

    public interface GroupBuilder {

        Group build();
    }

    @Override
    public void addObserver(Observer observer) {
        users.add((User) observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        users.remove((User) observer);
    }

    @Override
    public void notifyObservers(Runnable action, TestServiceImpl testService) {
        for (User user : users) {
            user.update(action, testService);
        }
    }

    public Set<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
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
