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

/**
 * The {@code Group} class represents a group with its associated properties.
 */
public class Group extends Entity implements Subject {

    private String name;
    private Set<User> users;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new {@code Group} instance.
     *
     * @param id                The unique identifier for the group.
     * @param users             The set of users in the group.
     * @param createdAt         The creation timestamp of the group.
     */
    private Group(UUID id, String name, Set<User> users, LocalDateTime createdAt) {
        super(id);
        this.name = name;
        this.users = new HashSet<>();
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code GroupBuilderId} instance to start building a {@code Group}.
     *
     * @return A {@code GroupBuilderId} instance.
     */
    public static GroupBuilderId builder() {
        return id -> name -> users -> createdAt -> () -> new Group(id, name, users, createdAt);
    }

    /**
     * Interface for the {@code Group} builder to set the ID.
     */
    public interface GroupBuilderId {
        GroupBuilderName id(UUID id);
    }

    /**
     * Interface for the {@code Group} builder to set the name.
     */
    public interface GroupBuilderName {
        GroupBuilderUsers name(String name);
    }

    /**
     * Interface for the {@code Group} builder to set users.
     */
    public interface GroupBuilderUsers {
        GroupBuilderCreatedAt users(Set<User> users);
    }

    /**
     * Interface for the {@code Group} builder to set the creation date.
     */
    public interface GroupBuilderCreatedAt {
        GroupBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Group} builder.
     */
    public interface GroupBuilder {
        Group build();
    }

    /**
     * Add observer to the user set.
     *
     * @param observer The observer to add.
     */
    @Override
    public void addObserver(Observer observer) {
        users.add((User) observer);
    }

    /**
     * Remove observer from the user set.
     *
     * @param observer The observer to add.
     */
    @Override
    public void removeObserver(Observer observer) {
        users.remove((User) observer);
    }

    /**
     * Notify all users and giving method for running
     *
     * @param action The action for running.
     * @param testService The service for running
     */
    @Override
    public void notifyObservers(Runnable action, TestServiceImpl testService) {
        for (User user : users) {
            user.update(action, testService);
        }
    }

    /**
     * Gets the set of users in the group.
     *
     * @return The set of users.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Adds a user to the group.
     *
     * @param user The user to add.
     */
    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Gets the name of the group.
     *
     * @return The group name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the group.
     *
     * @param name The new group name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the creation date of the group.
     *
     * @return The creation date of the group.
     */
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
