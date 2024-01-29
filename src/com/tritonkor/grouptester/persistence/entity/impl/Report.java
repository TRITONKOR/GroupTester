package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.UUID;

public class Report extends Entity implements Comparable<Report> {

    private String reportTitle;
    private final String testTitle;
    private final String groupName;
    private final LocalDateTime createdAt;
    private int minResult;
    private int maxResult;
    private int averageResult;

    private Report(UUID id, String reportTitle, String testTitle, String groupName, int minResult,
            int maxResult, int averageResult, LocalDateTime createdAt) {
        super(id);
        this.reportTitle = reportTitle;
        this.testTitle = testTitle;
        this.groupName = groupName;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
        this.createdAt = createdAt;
    }

    public static ReportBuilderId builder() {
        return id -> reportTitle -> testTitle -> groupName -> minResult -> maxResult -> averageResult -> createdAt -> () -> new Report(
                id, reportTitle, testTitle, groupName, minResult, maxResult, averageResult, createdAt);
    }

    public interface ReportBuilderId {

        ReportBuilderReportTitle id(UUID id);
    }

    public interface ReportBuilderReportTitle {

        ReportBuilderTestTitle reportTitle(String reportTitle);
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

    public String getReportTitle() { return reportTitle; }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
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
                "reportTitle='" + reportTitle + '\'' +
                ", testTitle='" + testTitle + '\'' +
                ", groupName='" + groupName + '\'' +
                ", createdAt=" + createdAt +
                ", minResult=" + minResult +
                ", maxResult=" + maxResult +
                ", averageResult=" + averageResult +
                '}';
    }
}
