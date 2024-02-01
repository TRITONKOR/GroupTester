package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The UserAddDto class represents a data transfer object for adding a user. It extends the Entity
 * class, providing identification through a UUID. This class includes essential user information
 * such as username, password, email, birthday, and role.
 */
public final class UserAddDto extends Entity {

    private final String username;
    private final String rawPassword;
    private final String email;
    private final LocalDate birthday;
    private final Role role;

    /**
     * Constructs a new UserAddDto with the specified parameters.
     *
     * @param id          the unique identifier for the user.
     * @param username    the username of the user.
     * @param rawPassword the raw password of the user.
     * @param email       the email address of the user.
     * @param birthday    the birthday of the user.
     */
    public UserAddDto(UUID id, String username, String rawPassword, String email,
            String birthday) {
        super(id);
        this.username = ValidationUtil.validateName(username);
        this.rawPassword = ValidationUtil.validatePassword(rawPassword);
        this.email = ValidationUtil.validateEmail(email);
        this.birthday = ValidationUtil.validateDate(birthday);
        this.role = Role.GENERAL;
    }

    /**
     * Constructs a new UserAddDto with the specified parameters, including the user's role.
     *
     * @param id          the unique identifier for the user.
     * @param username    the username of the user.
     * @param rawPassword the raw password of the user.
     * @param email       the email address of the user.
     * @param birthday    the birthday of the user.
     * @param role        the role of the user.
     */
    public UserAddDto(UUID id, String username, String rawPassword, String email,
            String birthday,
            Role role) {
        super(id);
        this.username = ValidationUtil.validateName(username);
        this.rawPassword = ValidationUtil.validatePassword(rawPassword);
        this.email = ValidationUtil.validateEmail(email);
        this.birthday = ValidationUtil.validateDate(birthday);
        this.role = role;
    }

    /**
     * Gets the username of the user.
     *
     * @return the username of the user.
     */
    public String username() {
        return username;
    }

    /**
     * Gets the raw password of the user.
     *
     * @return the raw password of the user.
     */
    public String rawPassword() {
        return rawPassword;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user.
     */
    public String email() {
        return email;
    }

    /**
     * Gets the birthday of the user.
     *
     * @return the birthday of the user.
     */
    public LocalDate birthday() {
        return birthday;
    }

    /**
     * Gets the role of the user.
     *
     * @return the role of the user.
     */
    public Role role() {
        return role;
    }
}
