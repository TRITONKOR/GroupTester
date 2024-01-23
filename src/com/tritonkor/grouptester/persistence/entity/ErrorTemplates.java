package com.tritonkor.grouptester.persistence.entity;

public enum ErrorTemplates {

    REQUIRED("Поле %s є обов'язкове до заповнення"),
    MIN_LENGTH("Поле %s не може бути меншим за %d симв."),
    MAX_LENGTH("Поле %s не може бути більшим за %d симв."),
    ONLY_LATIN("Поле %s може містити лише латинські літери"),
    PASSWORD(
            "Поле %s дозволяє тільки латинські миволи, хочаб одна буква з великої, одна з малої та "
                    + "хочаб одна цифра."),
    EMAIL("Поле %s дозволя тільки латинські літери, повинно бути @ і крапка повинна бути тільки одна"),
    BIRTHDAY("Поле %s не може містити дату з майбутнього");
    private String template;

    ErrorTemplates(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }
}
