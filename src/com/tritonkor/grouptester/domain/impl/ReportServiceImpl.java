package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ReportServiceImpl extends GenericService<Report> implements ReportService {

    private ReportRepository reportRepository;

    private Set<Grade> userGrades = new HashSet<>();

    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    public Report makeReport(String groupName, String testTitle) {

        int min = userGrades.stream().mapToInt(Grade::getGrade).min().orElse(0);
        int max = userGrades.stream().mapToInt(Grade::getGrade).max().orElse(0);
        int average = (int) userGrades.stream().mapToInt(Grade::getGrade).average().orElse(0);

        Report report = Report.builder().id(UUID.randomUUID()).testTitle(testTitle)
                .groupName(groupName)
                .minResult(min)
                .maxResult(max)
                .averageResult(average)
                .createdAt(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)).build();

        userGrades.clear();

        return report;
    }

    @Override
    public void saveGrade(Grade grade) {
        userGrades.add(grade);
    }

    @Override
    public void resetGrades() {
        userGrades.clear();
    }

    @Override
    public Set<Report> findAllByGroupName(String groupName) {

        return reportRepository.findAllByGroupName(groupName);
    }

    @Override
    public Set<Report> findAllByTestTitle(String testTitle) {
        return reportRepository.findAllByTestTitle(testTitle);
    }
}
