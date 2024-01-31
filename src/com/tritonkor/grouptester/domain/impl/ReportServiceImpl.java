package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.mindrot.bcrypt.BCrypt;

public class ReportServiceImpl extends GenericService<Report> implements ReportService {

    private ReportRepository reportRepository;


    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

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

        return  new ReportAddDto(UUID.randomUUID(), reportTitle, testTitle,
                groupName, minValue, maxValue, averageValue, usersResults,
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }

    @Override
    public Report findByName(String reportName) {
        return reportRepository.findByName(reportName).orElse(null);
    }

    @Override
    public Set<Report> findAllByGroupName(String groupName) {

        return reportRepository.findAllByGroupName(groupName);
    }

    @Override
    public Set<Report> findAllByTestTitle(String testTitle) {
        return reportRepository.findAllByTestTitle(testTitle);
    }

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
