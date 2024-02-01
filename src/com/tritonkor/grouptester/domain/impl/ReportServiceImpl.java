package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * The ReportServiceImpl class is an implementation of the ReportService interface, providing
 * functionality related to generating, finding, and managing reports in the application.
 */
public class ReportServiceImpl extends GenericService<Report> implements ReportService {

    private ReportRepository reportRepository;

    /**
     * Constructs a new ReportServiceImpl with the specified ReportRepository.
     *
     * @param reportRepository the repository used for report-related operations.
     */
    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    /**
     * Creates a new report based on the provided parameters and user results.
     *
     * @param reportTitle  the title of the report.
     * @param groupName    the name of the group associated with the report.
     * @param testTitle    the title of the test associated with the report.
     * @param usersResults the map of user results associated with the report.
     * @return a ReportAddDto containing the details of the generated report.
     */
    @Override
    public ReportAddDto makeReport(String reportTitle, String groupName, String testTitle,
            HashMap<String, Grade> usersResults) {

        int maxValue = Integer.MIN_VALUE;
        int minValue = Integer.MAX_VALUE;
        int sum = 0;

        for (Grade value : usersResults.values()) {
            maxValue = Math.max(maxValue, value.getGrade());
            minValue = Math.min(minValue, value.getGrade());
            sum += value.getGrade();
        }

        int averageValue = (int) sum / usersResults.size();

        return new ReportAddDto(UUID.randomUUID(), reportTitle, testTitle,
                groupName, minValue, maxValue, averageValue, usersResults,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    /**
     * Finds a report by its name.
     *
     * @param reportName the name of the report to find.
     * @return the found report or null if not found.
     */
    @Override
    public Report findByName(String reportName) {
        return reportRepository.findByName(reportName).orElse(null);
    }

    /**
     * Finds all reports associated with a specific group.
     *
     * @param groupName the name of the group.
     * @return a set of reports associated with the specified group.
     */
    @Override
    public Set<Report> findAllByGroupName(String groupName) {

        return reportRepository.findAllByGroupName(groupName);
    }

    /**
     * Finds all reports associated with a specific test.
     *
     * @param testTitle the title of the test.
     * @return a set of reports associated with the specified test.
     */
    @Override
    public Set<Report> findAllByTestTitle(String testTitle) {
        return reportRepository.findAllByTestTitle(testTitle);
    }

    /**
     * Adds a new report based on the provided ReportAddDto.
     *
     * @param reportAddDto the details of the report to add.
     * @return the added report.
     * @throws SignUpException if there is an error when saving the report.
     */
    @Override
    public Report add(ReportAddDto reportAddDto) {
        try {
            var report = Report.builder().id(reportAddDto.getId())
                    .reportTitle(reportAddDto.getReportTitle())
                    .testTitle(reportAddDto.getTestTitle()).groupName(reportAddDto.getGroupName())
                    .minResult(reportAddDto.getMinResult()).maxResult(reportAddDto.getMaxResult())
                    .averageResult(reportAddDto.getAverageResult())
                    .usersResults(reportAddDto.getUsersResults())
                    .createdAt(reportAddDto.getCreatedAt()).build();
            reportRepository.add(report);
            return report;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving a report: %s"
                    .formatted(e.getMessage()));
        }
    }
}
