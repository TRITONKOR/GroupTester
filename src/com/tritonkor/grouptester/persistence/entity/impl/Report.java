package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class Report extends Entity {

    private final Test test;
    private final Group group;
    private final LocalDate createdAt;
    private int minResult;
    private int maxResult;
    private int averageResult;

    public Report(UUID id, Test test, Group group, LocalDate createdAt, int minResult,
            int maxResult, int averageResult) {
        super(id);
        this.test = test;
        this.group = group;
        this.createdAt = createdAt;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
    }

    public Test getTest() {
        return test;
    }

    public Group getGroup() {
        return group;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public int getMinResult() {
        return minResult;
    }

    public void setMinResult(int minResult) {
        this.minResult = minResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getAverageResult() {
        return averageResult;
    }

    public void setAverageResult(int averageResult) {
        this.averageResult = averageResult;
    }

    @Override
    public String toString() {
        return "Report{" +
                "test=" + test +
                ", group=" + group +
                ", createdAt=" + createdAt +
                ", minResult=" + minResult +
                ", maxResult=" + maxResult +
                ", averageResult=" + averageResult +
                '}';
    }
}
