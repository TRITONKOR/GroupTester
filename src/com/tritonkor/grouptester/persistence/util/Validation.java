package com.tritonkor.grouptester.persistence.util;

import com.tritonkor.grouptester.persistence.entity.ErrorTemplates;
import com.tritonkor.grouptester.persistence.exception.EntityArgumentException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class Validation {
    /**
     * Валідація тексту
     *
     * @param text
     * @throws EntityArgumentException в разі, якщо є помилка в text
     */
    public static String validateText(String text, List<String> errors, int maxSynbols) {

        final String templateName = "тексту";

        if (text.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }
        if (text.length() < 4) {
            errors.add(ErrorTemplates.MIN_LENGTH.getTemplate().formatted(templateName, 4));
        }
        if (text.length() > 24) {
            errors.add(ErrorTemplates.MAX_LENGTH.getTemplate().formatted(templateName, maxSynbols));
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        if (pattern.matcher(text).matches()) {
            errors.add(ErrorTemplates.ONLY_LATIN.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return text;
    }

    public static String validatePassword(String password, List<String> errors) {
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

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return password;
    }

    public static String validateEmail(String email, List<String> errors) {
        final String templateName = "електронної пошти";

        if (email.isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        Pattern pattern = Pattern.compile(
                "^[a-zA-Z0-9_%+-]+(?:[a-zA-Z0-9_%+-]+)*@[a-zA-Z0-9.-]+[a-zA-Z]{2,}$");
        if (pattern.matcher(email).matches()) {
            errors.add(ErrorTemplates.EMAIL.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return email;
    }

    public static LocalDate validateDate(LocalDate birthday, List<String> errors) {
        final String templateName = "дати";

        if (birthday.toString().isBlank()) {
            errors.add(ErrorTemplates.REQUIRED.getTemplate().formatted(templateName));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsedDate = LocalDate.parse(birthday.toString(), formatter);

        LocalDate currentDate = LocalDate.now();
        if (parsedDate.isAfter(currentDate)) {
            errors.add(ErrorTemplates.BIRTHDAY.getTemplate().formatted(templateName));
        }

        if (!errors.isEmpty()) {
            throw new EntityArgumentException(errors);
        }

        return birthday;
    }
}
