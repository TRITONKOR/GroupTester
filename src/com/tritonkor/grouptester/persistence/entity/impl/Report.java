package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Report extends Entity implements Comparable<Report> {

    private final String testTitle;
    private final String groupName;
    private final LocalDateTime createdAt;
    private int minResult;
    private int maxResult;
    private int averageResult;

    private Report(UUID id, String testTitle, String groupName, int minResult,
            int maxResult, int averageResult, LocalDateTime createdAt) {
        super(id);
        this.testTitle = testTitle;
        this.groupName = groupName;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
        this.createdAt = createdAt;
    }

    public static ReportBuilderId builder() {
        return id -> testTitle -> groupName -> minResult -> maxResult -> averageResult -> createdAt -> () -> new Report(
                id, testTitle, groupName, minResult, maxResult, averageResult, createdAt);
    }

    public interface ReportBuilderId {

        ReportBuilderTestTitle id(UUID id);
    }

    public interface ReportBuilderTestTitle {

        ReportBuilderGroupName testTitle(String testTitle);
    }

    public interface ReportBuilderGroupName {

        ReportBuilderMinResult groupName(String groupName);
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

        ReportBuilder createdAt(LocalDateTime createdAt);
    }

    public interface ReportBuilder {

        Report build();
    }


    public interface GroupBuilder {

        Group build();
    }

    public String getTestTitle() {
        return testTitle;
    }

    public String getGroupName() {
        return groupName;
    }

    public LocalDateTime getCreatedAt() {
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
                "testTitle=" + testTitle +
                ", groupName=" + groupName +
                ", createdAt=" + createdAt +
                ", minResult=" + minResult +
                ", maxResult=" + maxResult +
                ", averageResult=" + averageResult +
                '}';
    }
}
