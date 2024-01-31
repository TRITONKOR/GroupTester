package com.tritonkor.grouptester.domain.dto;

import com.tritonkor.grouptester.domain.ValidationUtil;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

public class ReportAddDto extends Entity {

    private String reportTitle;
    private final String testTitle;
    private final String groupName;
    private int minResult;
    private int maxResult;
    private int averageResult;
    private final HashMap<String, Grade> usersResults;
    private final LocalDateTime createdAt;

    public ReportAddDto(UUID id, String reportTitle, String testTitle, String groupName,
            int minResult, int maxResult, int averageResult, HashMap<String, Grade> usersResults, LocalDateTime createdAt) {
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

    public HashMap<String, Grade> getUsersResults() {
        return usersResults;
    }

    public String getReportTitle() { return reportTitle; }

    public String getTestTitle() {
        return testTitle;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getMinResult() {
        return minResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getAverageResult() {
        return averageResult;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
