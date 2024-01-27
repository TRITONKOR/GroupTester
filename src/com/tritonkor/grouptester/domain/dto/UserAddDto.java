package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.Validation;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import java.time.LocalDate;
import java.util.UUID;

public final class UserAddDto extends Entity {

    private final String username;
    private final String rawPassword;
    private final String email;
    private final LocalDate birthday;
    private final Role role;

    public UserAddDto(UUID id, String username, String rawPassword, String email,
            String birthday) {
        super(id);
        this.username = Validation.validateText(username);
        this.rawPassword = Validation.validatePassword(rawPassword);
        this.email = Validation.validateEmail(email);
        this.birthday = Validation.validateDate(LocalDate.parse(birthday));
        this.role = Role.GENERAL;
    }

    public UserAddDto(UUID id, String username, String rawPassword, String email,
            LocalDate birthday,
            Role role) {
        super(id);
        this.username = username;
        this.rawPassword = rawPassword;
        this.email = email;
        this.birthday = birthday;
        this.role = role;
    }

    public String username() {
        return username;
    }

    public String rawPassword() {
        return rawPassword;
    }

    public String email() {
        return email;
    }

    public LocalDate birthday() {
        return birthday;
    }

    public Role role() {
        return role;
    }
}
