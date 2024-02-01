package com.tritonkor.grouptester.persistence.entity;

/**
 * The {@code ErrorTemplates} enum provides templates for generating error messages related to various constraints.
 * Each constant in this enum represents a specific error scenario and includes a template string with placeholders.
 * These templates are designed to be used when constructing error messages for validation and error handling.
 */
public enum ErrorTemplates {

    /**
     * Indicates that a required field is missing.
     * Template: "The %s field is required."
     */
    REQUIRED("The %s field is required"),

    /**
     * Specifies the minimum length constraint for a field.
     * Template: "The %s field cannot be less than %d characters."
     */
    MIN_LENGTH("The %s field cannot be less than %d characters."),

    /**
     * Specifies the maximum length constraint for a field.
     * Template: "The %s field cannot be more than %d characters."
     */
    MAX_LENGTH("The %s field cannot be more than %d characters."),

    /**
     * Specifies that a field can only contain Latin letters.
     * Template: "The %s field can contain only Latin letters."
     */
    ONLY_LATIN("The %s field can contain only Latin letters"),

    /**
     * Specifies requirements for a password field, including Latin characters, uppercase, lowercase, and digits.
     * Template: "The %s field allows only Latin characters, at least one uppercase letter, one lowercase letter, and at least one digit."
     */
    PASSWORD(
            "The %s field allows only Latin characters, at least one uppercase letter, one lowercase"
                    + " letter, and at least one digit."),

    /**
     * Specifies requirements for an email field, including Latin letters, @, and only one dot.
     * Template: "The %s field allows only Latin letters, must be @, and there must be only one dot."
     */
    EMAIL("The %s field allows only Latin letters, must be @ and there must be only one dot"),

    /**
     * Specifies a constraint for a date field, stating that it cannot contain a date from the future.
     * Template: "The %s field cannot contain a date from the future."
     */
    DATE("The %s field cannot contain a date from the future");
    private String template;

    /**
     * Constructs an {@code ErrorTemplates} enum constant with the specified template.
     *
     * @param template The template string for the error type.
     */
    ErrorTemplates(String template) {
        this.template = template;
    }

    /**
     * Gets the template associated with the error type.
     *
     * @return The template string.
     */
    public String getTemplate() {
        return template;
    }
}
