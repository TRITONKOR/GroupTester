package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import java.util.HashMap;
import java.util.Set;

/**
 * The ReportService interface defines the contract for report-related operations. It provides
 * methods for finding reports by name, group name, or test title, adding new reports, creating a
 * new report based on specific parameters, and other report-related functionalities.
 */
public interface ReportService extends Service<Report> {

    /**
     * Finds a report by its name.
     *
     * @param reportName The name of the report to be found.
     * @return The Report object representing the found report, or null if no report is found with
     * the given name.
     */
    Report findByName(String reportName);

    /**
     * Finds all reports associated with a specific group.
     *
     * @param groupName The name of the group for which to find reports.
     * @return A set of Report objects representing the reports associated with the given group.
     */
    Set<Report> findAllByGroupName(String groupName);

    /**
     * Finds all reports associated with a specific test title.
     *
     * @param testTitle The title of the test for which to find reports.
     * @return A set of Report objects representing the reports associated with the given test
     * title.
     */
    Set<Report> findAllByTestTitle(String testTitle);

    /**
     * Adds a new report based on the provided ReportAddDto.
     *
     * @param reportAddDto The ReportAddDto containing the details for creating a new report.
     * @return The Report object representing the newly added report.
     */
    Report add(ReportAddDto reportAddDto);

    /**
     * Creates a new report based on specific parameters.
     *
     * @param reportTitle  The title of the report.
     * @param groupName    The name of the group associated with the report.
     * @param testTitle    The title of the test associated with the report.
     * @param usersResults A map containing usernames and their corresponding grades.
     * @return The ReportAddDto representing the newly created report.
     */
    ReportAddDto makeReport(String reportTitle, String groupName, String testTitle,
            HashMap<String, Grade> usersResults);
}
