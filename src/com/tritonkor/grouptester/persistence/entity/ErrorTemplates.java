package com.tritonkor.grouptester.persistence.entity;

public enum ErrorTemplates {

    REQUIRED("Поле %s є обов'язкове до заповнення"),
    MIN_LENGTH("Поле %s не може бути меншим за %d симв."),
    MAX_LENGTH("Поле %s не може бути більшим за %t симв."),
    ONLY_LATIN("Поле %s лише латинські літери"),
    PASSWORD("Поле");
    private String template;

    ErrorTemplates(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
