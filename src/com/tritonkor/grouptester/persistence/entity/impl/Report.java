package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDate;
import java.util.UUID;

public class Report extends Entity implements Comparable<Report> {

    private final Test test;
    private final Group group;
    private final LocalDate createdAt;
    private int minResult;
    private int maxResult;
    private int averageResult;

    private Report(UUID id, Test test, Group group, int minResult,
            int maxResult, int averageResult, LocalDate createdAt) {
        super(id);
        this.test = test;
        this.group = group;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
        this.createdAt = createdAt;
    }

    public static ReportBuilderId builder() {
        return id -> test -> group -> minResult -> maxResult -> averageResult -> createdAt -> () -> new Report(
                id, test, group, minResult, maxResult, averageResult, createdAt);
    }

    public interface ReportBuilderId {

        ReportBuilderTest id(UUID id);
    }

    public interface ReportBuilderTest {

        ReportBuilderGroup test(Test test);
    }

    public interface ReportBuilderGroup {

        ReportBuilderMinResult group(Group group);
    }

    public interface ReportBuilderMinResult {

        ReportBuilderMaxResult minResult(int minResult);
    }

    public interface ReportBuilderMaxResult {

        ReportBuilderAverageResult maxResult(int maxResult);
    }

    public interface ReportBuilderAverageResult {

        ReportBuilderCreatedAt averageResult(int averageResult);
    }

    public interface ReportBuilderCreatedAt {

        ReportBuilder createdAt(LocalDate createdAt);
    }

    public interface ReportBuilder {

        Report build();
    }


    public interface GroupBuilder {

        Group build();
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
    public int compareTo(Report o) {
        return this.createdAt.compareTo(o.createdAt);
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
