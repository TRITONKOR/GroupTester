package com.tritonkor.grouptester.persistence.entity.impl;

public class Grade {

    private final int MAX_GRADE = 100;

    private int grade;

    public Grade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade:" + grade + "/" + MAX_GRADE;
    }
}
