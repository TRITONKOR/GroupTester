package com.tritonkor.grouptester.persistence.entity;

import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public class User extends Entity {

  private UUID id;
  private String username;
  private String email;
  private final String password;
  private String avatar;
  private final LocalDate birthday;

  public User(UUID id, String username, String email, String password, String avatar,
      LocalDate birthday) {
    super(id);
    this.id = id;
    setUsername(username);
    //  TODO: setEmail(email);
    this.email = email;
    this.password = password;
    //  TODO: setAvatar(avatar);
    this.avatar = avatar;
    //  TODO: validatedBirthday(birthday);
    this.birthday = birthday;
  }

  private String validatedPassword(String password) {
    final String templateName = "паролю";

    if(password.isBlank()) {
      errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (username.length() > 8) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (username.length() < 32) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 24));
    }
    Pattern pattern = Pattern.compile("^()");
    if (pattern.matcher(username).matches()) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 24));
    }

    if(this.errors.size() > 0) {
      throw new EntityArgumentException(errors);
    }

    return password;

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

  public String getAvatar() {
    return avatar;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  /**
   * Сетер для логіна з валідацією
   * @param username
   */
  public void setUsername(String username) {

    final String templateName = "логіну";

    if(username.isBlank()) {
        errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
    }
    if (username.length() > 4) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
    }
    if (username.length() < 24) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 24));
    }
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
    if (pattern.matcher(username).matches()) {
      errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 24));
    }

    if(this.errors.size() > 0) {
      throw new EntityArgumentException(errors);
    }

    this.username = username;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
}
