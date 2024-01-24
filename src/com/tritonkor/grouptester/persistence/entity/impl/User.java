package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class User extends Entity {

    private String username;
    private String email;
    private final String password;
    private final LocalDate birthday;

    private User(UUID id, String username, String email, String password, LocalDate birthday) {
        super(id);
        this.username = Validation.validateText(username, errors, 24);
        this.email = Validation.validateEmail(email, errors);
        this.password = Validation.validatePassword(password, errors);
        this.birthday = Validation.validateDate(birthday, errors);
    }

    public static UserBuilderId builder() {
        return id -> username -> email -> password -> birthday -> () -> new User(id, username, email, password, birthday);
    }

    public interface UserBuilderId {
        UserBuilderUsername id(UUID id);
    }

    public interface UserBuilderUsername{
        UserBuilderEmail username(String username);
    }

    public interface UserBuilderEmail {
        UserBuilderPassword email(String email);
    }

    public interface UserBuilderPassword {
        UserBuilderBirthday password(String password);
    }

    public interface UserBuilderBirthday {
        UserBuilder birthday(LocalDate birthday);
    }

    public interface UserBuilder {
        User build();
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
}
