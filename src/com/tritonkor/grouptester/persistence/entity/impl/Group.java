package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.domain.observer.Subject;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.domain.observer.Observer;
import com.tritonkor.grouptester.domain.Validation;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Group extends Entity implements Subject {

    private final LocalDateTime createdAt;

    private String testNameToPerform = null;

    private String name;
    private Set<User> users;
    private Set<Observer> observers = new HashSet<>();

    private Group(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = Validation.validateText(name, 10);
        this.users = new HashSet<>();
        this.createdAt = Validation.validateDateTime(createdAt);
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

        observers.add(observer);
        users.add((User) observer);
    }

    @Override
    public void removeObserver(Observer observer) {

        observers.remove(observer);
        users.remove((User) observer);
    }

    @Override
    public void notifyObservers(String currentTest) {
        for(Observer observer : observers) {
            observer.update(currentTest);
        }
    }

    public String getTestNameToPerform() {
        return testNameToPerform;
    }

    public void setTestNameToPerform(String testNameToPerform) {
        this.testNameToPerform = testNameToPerform;
        notifyObservers(testNameToPerform);
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
