package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.domain.impl.TestServiceImpl;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.domain.observer.Observer;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * The {@code User} class represents a user in the system.
 */
public class User extends Entity implements Observer, Comparable {

    private String username;
    private String email;
    private final Role role;
    private final String password;
    private final LocalDate birthday;

    /**
     * Constructs a new {@code User} instance.
     *
     * @param id       The unique identifier for the user.
     * @param username The username of the user.
     * @param email    The email address of the user.
     * @param password The password of the user.
     * @param birthday The birthday of the user.
     * @param role     The role of the user.
     */
    public User(UUID id, String username, String email, String password, LocalDate birthday,
            Role role) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.role = role;
    }

    /**
     * Returns a {@code UserBuilderId} instance to start building a {@code User}.
     *
     * @return A {@code UserBuilderId} instance.
     */
    public static UserBuilderId builder() {
        return id -> username -> email -> password -> birthday -> role -> () -> new User(id,
                username,
                email, password, birthday, role);
    }

    /**
     * Interface for the {@code User} builder to set the ID.
     */
    public interface UserBuilderId {

        UserBuilderUsername id(UUID id);
    }

    /**
     * Interface for the {@code User} builder to set the username.
     */
    public interface UserBuilderUsername {

        UserBuilderEmail username(String username);
    }

    /**
     * Interface for the {@code User} builder to set the email.
     */
    public interface UserBuilderEmail {

        UserBuilderPassword email(String email);
    }

    /**
     * Interface for the {@code User} builder to set the password.
     */
    public interface UserBuilderPassword {

        UserBuilderBirthday password(String password);
    }

    /**
     * Interface for the {@code User} builder to set the birthday.
     */
    public interface UserBuilderBirthday {

        UserBuilderRole birthday(LocalDate birthday);
    }

    /**
     * Interface for the {@code User} builder to set the user role.
     */
    public interface UserBuilderRole {

        UserBuilder role(Role role);
    }

    /**
     * Interface for the final steps of the {@code Result} builder.
     */
    public interface UserBuilder {

        User build();
    }

    /**
     * Gets the role of the user.
     *
     * @return The user's role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Gets the unique identifier of the user.
     *
     * @return The user's identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the username of the user.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets the password of the user.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the birthday of the user.
     *
     * @return The user's birthday.
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void update(Runnable runTest, TestServiceImpl testService) {
        runTest.run();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", id=" + id +
                '}';
    }

    /**
     * The {@code Role} enum represents the roles that a user can have.
     */
    public enum Role {

        ADMIN("admin", Map.of(
                EntityName.USER, new Permission(true, true, true, true),
                EntityName.TEST, new Permission(true, true, true, true),
                EntityName.GROUP, new Permission(true, true, true, true),
                EntityName.RESULT, new Permission(true, true, true, true),
                EntityName.REPORT, new Permission(true, true, true, true))),
        GENERAL("general", Map.of(
                EntityName.USER, new Permission(false, false, false, false),
                EntityName.TEST, new Permission(false, false, false, true),
                EntityName.GROUP, new Permission(false, false, false, true),
                EntityName.RESULT, new Permission(false, false, false, true),
                EntityName.REPORT, new Permission(false, false, false, false)));
        private String name;
        private Map<EntityName, Permission> permissions;

        /**
         * Constructs a new {@code Role} enum instance.
         *
         * @param name        The name of the role.
         * @param permissions The permissions associated with the role.
         */
        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        /**
         * Gets the permissions associated with the role.
         *
         * @return The role's permissions.
         */
        public Map<EntityName, Permission> getPermissions() { return permissions; }

        /**
         * The {@code EntityName} enum represents the entities for which permissions can be assigned.
         */
        public enum EntityName {
            USER,
            TEST,
            GROUP,
            RESULT,
            REPORT;
        }

        /**
         * The {@code Permission} record represents the permissions granted for a specific entity.
         */
        public record Permission(boolean canAdd,
                                  boolean canEdit,
                                  boolean canDelete,
                                  boolean canRead) {
        }
    }
}
