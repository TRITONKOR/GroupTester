package com.tritonkor.grouptester.persistence.entity;

public enum ErrorTemplates {

    REQUIRED("The %s field is required"),
    MIN_LENGTH("The %s field cannot be less than %d characters."),
    MAX_LENGTH("The %s field cannot be more than %d characters."),
    ONLY_LATIN("The %s field can contain only Latin letters"),
    PASSWORD(
            "The %s field allows only Latin characters, at least one uppercase letter, one lowercase"
                    + " letter, and at least one digit."),
    EMAIL("The %s field allows only Latin letters, must be @ and there must be only one dot"),
    DATE("The %s field cannot contain a date from the future");
    private String template;

    ErrorTemplates(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
