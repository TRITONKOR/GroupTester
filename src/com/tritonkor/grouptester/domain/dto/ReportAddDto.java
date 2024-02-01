package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

/**
 * The ReportAddDto class represents a data transfer object for adding a report. This class includes
 * information such as report title, test title, group name, minimum result, maximum result, average
 * result, user results, and creation timestamp.
 */
public class ReportAddDto extends Entity {

    private String reportTitle;
    private final String testTitle;
    private final String groupName;
    private int minResult;
    private int maxResult;
    private int averageResult;
    private final HashMap<String, Grade> usersResults;
    private final LocalDateTime createdAt;

    /**
     * Constructs a new ReportAddDto with the specified parameters.
     *
     * @param id            the unique identifier for the report.
     * @param reportTitle   the title of the report.
     * @param testTitle     the title of the associated test.
     * @param groupName     the name of the associated group.
     * @param minResult     the minimum result in the report.
     * @param maxResult     the maximum result in the report.
     * @param averageResult the average result in the report.
     * @param usersResults  a mapping of usernames to their respective grades.
     * @param createdAt     the creation timestamp of the report.
     */
    public ReportAddDto(UUID id, String reportTitle, String testTitle, String groupName,
            int minResult, int maxResult, int averageResult, HashMap<String, Grade> usersResults,
            LocalDateTime createdAt) {
        super(id);
        this.reportTitle = ValidationUtil.validateTitle(reportTitle);
        this.testTitle = ValidationUtil.validateTitle(testTitle);
        this.groupName = ValidationUtil.validateName(groupName);
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.averageResult = averageResult;
        this.usersResults = usersResults;
        this.createdAt = ValidationUtil.validateDateTime(createdAt);
    }

    /**
     * Gets the user results mapping.
     *
     * @return a mapping of usernames to their respective grades.
     */
    public HashMap<String, Grade> getUsersResults() {
        return usersResults;
    }

    /**
     * Gets the title of the report.
     *
     * @return the title of the report.
     */
    public String getReportTitle() {
        return reportTitle;
    }

    /**
     * Gets the title of the associated test.
     *
     * @return the title of the associated test.
     */
    public String getTestTitle() {
        return testTitle;
    }

    /**
     * Gets the name of the associated group.
     *
     * @return the name of the associated group.
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Gets the minimum result in the report.
     *
     * @return the minimum result in the report.
     */
    public int getMinResult() {
        return minResult;
    }

    /**
     * Gets the maximum result in the report.
     *
     * @return the maximum result in the report.
     */
    public int getMaxResult() {
        return maxResult;
    }

    /**
     * Gets the average result in the report.
     *
     * @return the average result in the report.
     */
    public int getAverageResult() {
        return averageResult;
    }

    /**
     * Gets the creation timestamp of the report.
     *
     * @return the creation timestamp of the report.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
