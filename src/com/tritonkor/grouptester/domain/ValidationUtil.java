package com.tritonkor.grouptester.domain;

import com.tritonkor.grouptester.persistence.entity.ErrorTemplates;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * The ValidationUtil class provides utility methods for validating different types of data. It
 * includes validation methods for names, titles, passwords, emails, dates, and date and time.
 */
public class ValidationUtil {

    /**
     * Validates the provided name.
     *
     * @param text The name to be validated.
     * @return The validated name.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static String validateName(String text) {
        Set<String> errors = new HashSet<>();
        final String templateName = "name";

        if (Objects.isNull(text)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        if (text.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (text.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (text.length() > 30) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 30));
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9_.#$%^:№\\s\\p{P}]+$");
        if (!pattern.matcher(text).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return text;
    }

    /**
     * Validates the provided title.
     *
     * @param text The title to be validated.
     * @return The validated title.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static String validateTitle(String text) {
        Set<String> errors = new HashSet<>();
        final String templateName = "name";

        if (Objects.isNull(text)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        if (text.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (text.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (text.length() > 100) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, 100));
        }

        Pattern pattern = Pattern.compile("^[A-Za-z0-9_.#$%^:№\\s\\p{P}]+$");
        if (!pattern.matcher(text).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return text;
    }

    /**
     * Validates the provided password.
     *
     * @param password The password to be validated.
     * @return The validated password.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static String validatePassword(String password) {
        Set<String> errors = new HashSet<>();
        final String templateName = "password";

        if (Objects.isNull(password)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

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

        if (!pattern.matcher(password).matches()) {
            errors.add(ErrorTemplates.PASSWORD.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return password;
    }

    /**
     * Validates the provided email.
     *
     * @param email The email to be validated.
     * @return The validated email.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static String validateEmail(String email) {
        Set<String> errors = new HashSet<>();
        final String templateName = "email";

        if (Objects.isNull(email)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        Pattern pattern = Pattern.compile(
                "^[a-zA-Z0-9._%+-]+(?:\\.[a-zA-Z0-9._%+-]+)*@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!pattern.matcher(email).matches()) {
            errors.add(ErrorTemplates.EMAIL.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return email;
    }

    /**
     * Validates the provided date.
     *
     * @param date The date to be validated.
     * @return The validated date.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static LocalDate validateDate(String date) {
        Set<String> errors = new HashSet<>();
        final String templateName = "date";

        if (Objects.isNull(date)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        if (date.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, formatter);

            LocalDate currentDate = LocalDate.now();
            if (parsedDate.isAfter(currentDate)) {
                errors.add(ErrorTemplates.DATE.getTemplate().formatted(templateName));
            }
        } catch (Exception e) {
            throw new EntityArgumentException("Wrong data format");
        }
        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return LocalDate.parse(date);
    }

    /**
     * Validates the provided date and time.
     *
     * @param date The date and time to be validated.
     * @return The validated date and time.
     * @throws EntityArgumentException If there is an error in the validation.
     */
    public static LocalDateTime validateDateTime(LocalDateTime date) {
        Set<String> errors = new HashSet<>();
        final String templateName = "date and time";

        if (Objects.isNull(date)) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        if (date.toString().isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime parsedDate = LocalDateTime.parse(date.format(formatter), formatter);

        LocalDateTime currentDate = LocalDateTime.parse(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES).format(formatter), formatter);
        if (parsedDate.isAfter(currentDate)) {
            errors.add(ErrorTemplates.DATE.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(combineErrorMessages(errors));
        }

        return date;
    }

    /**
     * Combines error messages from a set into a single string.
     *
     * @param errors The set of error messages.
     * @return The combined error message.
     */
    private static String combineErrorMessages(Set<String> errors) {
        String message = "";

        for (String error : errors) {
            message = message + error + '\n';
        }

        return message;
    }
}
