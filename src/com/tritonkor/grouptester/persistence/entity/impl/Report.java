package com.tritonkor.grouptester.persistence.entity.impl;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * The {@code Report} class represents a report containing information about test results for a group.
 */
public class Report extends Entity implements Comparable<Report> {

    private String reportTitle;
    private final String testTitle;
    private final String groupName;
    private final LocalDateTime createdAt;
    private final HashMap<String, Grade> usersResults;
    private int minResult;
    private int maxResult;
    private int averageResult;

    /**
     * Constructs a new {@code Report} instance.
     *
     * @param id                      The unique identifier for the report.
     * @param reportTitle             The title of the report.
     * @param testTitle               The title of the test
     * @param groupName               The name of the group.
     * @param minResult               The minimum result from the users.
     * @param maxResult               The maximum result from the users.
     * @param averageResult           The average result from all.
     * @param usersResults            The usersResults.
     * @param createdAt               The creation timestamp of the report.
     */
    private Report(UUID id, String reportTitle, String testTitle, String groupName, int minResult,
            int maxResult, int averageResult, HashMap<String, Grade> usersResults, LocalDateTime createdAt) {
        super(id);
        this.reportTitle = reportTitle;
        this.testTitle = testTitle;
        this.groupName = groupName;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
        this.usersResults = usersResults;
        this.createdAt = createdAt;
    }

    /**
     * Returns a {@code ReportBuilderId} instance to start building a {@code Report}.
     *
     * @return A {@code ReportBuilderId} instance.
     */
    public static ReportBuilderId builder() {
        return id -> reportTitle -> testTitle -> groupName -> minResult -> maxResult -> averageResult -> usersResults -> createdAt -> () -> new Report(
                id, reportTitle, testTitle, groupName, minResult, maxResult, averageResult, usersResults, createdAt);
    }

    /**
     * Interface for the {@code Report} builder to set the ID.
     */
    public interface ReportBuilderId {

        ReportBuilderReportTitle id(UUID id);
    }

    /**
     * Interface for the {@code Report} builder to set the report title.
     */
    public interface ReportBuilderReportTitle {

        ReportBuilderTestTitle reportTitle(String reportTitle);
    }

    /**
     * Interface for the {@code Report} builder to set the test title.
     */
    public interface ReportBuilderTestTitle {

        ReportBuilderGroupName testTitle(String testTitle);
    }

    /**
     * Interface for the {@code Report} builder to set the group name.
     */
    public interface ReportBuilderGroupName {

        ReportBuilderMinResult groupName(String groupName);
    }

    /**
     * Interface for the {@code Report} builder to set the minimum result.
     */
    public interface ReportBuilderMinResult {

        ReportBuilderMaxResult minResult(int minResult);
    }

    /**
     * Interface for the {@code Report} builder to set the maximum result.
     */
    public interface ReportBuilderMaxResult {

        ReportBuilderAverageResult maxResult(int maxResult);
    }

    /**
     * Interface for the {@code Report} builder to set the average result.
     */
    public interface ReportBuilderAverageResult {

        ReportBuilderUsersResults averageResult(int averageResult);
    }

    /**
     * Interface for the {@code Report} builder to set users results.
     */
    public interface ReportBuilderUsersResults {

        ReportBuilderCreatedAt usersResults(HashMap<String, Grade> usersResults);
    }

    /**
     * Interface for the {@code Report} builder to set the creation date.
     */
    public interface ReportBuilderCreatedAt {

        ReportBuilder createdAt(LocalDateTime createdAt);
    }

    /**
     * Interface for the final steps of the {@code Report} builder.
     */
    public interface ReportBuilder {

        Report build();
    }

    /**
     * Gets the map of user results associated with the report.
     *
     * @return The map of user results.
     */
    public HashMap<String, Grade> getUsersResults() {
        return usersResults;
    }

    /**
     * Gets the title of the report.
     *
     * @return The report title.
     */
    public String getReportTitle() { return reportTitle; }

    /**
     * Sets the title of the report.
     *
     * @param reportTitle The new report title.
     */
    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    /**
     * Gets the title of the test associated with the report.
     *
     * @return The test title.
     */
    public String getTestTitle() {
        return testTitle;
    }

    /**
     * Gets the name of the group associated with the report.
     *
     * @return The group name.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Gets the creation timestamp of the report.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the minimum result in the report.
     *
     * @return The minimum result.
     */
    public int getMinResult() {
        return minResult;
    }

    /**
     * Sets the minimum result in the report.
     *
     * @param minResult The new minimum result.
     */
    public void setMinResult(int minResult) {
        this.minResult = minResult;
    }

    /**
     * Gets the maximum result in the report.
     *
     * @return The maximum result.
     */
    public int getMaxResult() {
        return maxResult;
    }

    /**
     * Sets the maximum result in the report.
     *
     * @param maxResult The new maximum result.
     */
    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    /**
     * Gets the average result in the report.
     *
     * @return The average result.
     */
    public int getAverageResult() {
        return averageResult;
    }

    /**
     * Sets the average result in the report.
     *
     * @param averageResult The new average result.
     */
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
