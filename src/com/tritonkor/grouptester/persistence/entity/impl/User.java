package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.ErrorTemplates;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
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
        setUsername(username);
        this.email = validatedEmail(email);
        this.password = validatedPassword(password);
        this.birthday = validatedBirthday(birthday);
    }

    /**
     * Сетер для логіна з валідацією
     *
     * @param username
     * @throws EntityArgumentException в разі, якщо є помилка в username
     */
    public void setUsername(String username) {

        final String templateName = "логіну";

        if (username.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (username.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (username.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 24));
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (pattern.matcher(username).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        this.username = username;
    }

    private String validatedPassword(String password) {
        final String templateName = "паролю";

        if (password.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (password.length() < 8) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 8));
        }
        if (password.length() > 32) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 32));
        }
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$");
        if (pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return password;
    }

    private String validatedEmail(String email) {
        final String templateName = "електронної пошти";

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        Pattern pattern = Pattern.compile(
                "^[a-zA-Z0-9_%+-]+(?:[a-zA-Z0-9_%+-]+)*@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$");
        if (pattern.matcher(email).matches()) {
            errors.add(ErrorTemplates.EMAIL.getTemplate().formatted(templateName));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return email;
    }

    private LocalDate validatedBirthday(LocalDate birthday) {
        final String templateName = "дня народження";

        if (birthday.toString().isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(birthday.toString(), formatter);

        LocalDate currentDate = LocalDate.now();
        if (parsedDate.isAfter(currentDate)) {
            errors.add(ErrorTemplates.BIRTHDAY.getTemplate().formatted(templateName));
        }

        if (!this.errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return birthday;
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
