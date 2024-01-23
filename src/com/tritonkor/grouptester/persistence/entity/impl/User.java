package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.ErrorTemplates;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import com.tritonkor.grouptester.persistence.util.Validation;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

    private String username;
    private String email;
    private final String password;
    private final LocalDate birthday;

    public User(UUID id, String username, String email, String password, LocalDate birthday) {
        super(id);
        this.username = Validation.validateText(username, errors, 24);
        this.email = Validation.validateEmail(email, errors);
        this.password = Validation.validatePassword(password, errors);
        this.birthday = Validation.validateDate(birthday, errors);
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
