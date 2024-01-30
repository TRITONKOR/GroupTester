package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.domain.impl.TestServiceImpl;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.domain.observer.Observer;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class User extends Entity implements Observer {

    private String username;
    private String email;
    private final Role role;
    private final String password;
    private final LocalDate birthday;

    public User(UUID id, String username, String email, String password, LocalDate birthday,
            Role role) {
        super(id);
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.role = role;
    }

    public static UserBuilderId builder() {
        return id -> username -> email -> password -> birthday -> role -> () -> new User(id,
                username,
                email, password, birthday, role);
    }

    public interface UserBuilderId {

        UserBuilderUsername id(UUID id);
    }

    public interface UserBuilderUsername {

        UserBuilderEmail username(String username);
    }

    public interface UserBuilderEmail {

        UserBuilderPassword email(String email);
    }

    public interface UserBuilderPassword {

        UserBuilderBirthday password(String password);
    }

    public interface UserBuilderBirthday {

        UserBuilderRole birthday(LocalDate birthday);
    }

    public interface UserBuilderRole {

        UserBuilder role(Role role);
    }

    public interface UserBuilder {

        User build();
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthday() {
        return birthday;
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

        Role(String name, Map<EntityName, Permission> permissions) {
            this.name = name;
            this.permissions = permissions;
        }

        public enum EntityName {
            USER,
            TEST,
            GROUP,
            RESULT,
            REPORT;
        }

        public record Permission(boolean canAdd,
                                  boolean canEdit,
                                  boolean canDelete,
                                  boolean canRead) {

        }

    }
}
