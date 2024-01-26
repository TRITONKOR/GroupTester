package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

public class ReportServiceImpl extends GenericService<Report> implements ReportService {

    private ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
        this.reportRepository = reportRepository;
    }

    public Report makeReport(String groupName, String testTitle, Set<Grade> grades) {
        int min = grades.stream().mapToInt(Grade::getGrade).min().orElse(0);
        int max = grades.stream().mapToInt(Grade::getGrade).max().orElse(0);
        int average = (int) grades.stream().mapToInt(Grade::getGrade).average().orElse(0);

        return Report.builder().id(UUID.randomUUID()).testTitle(testTitle).groupName(groupName)
                .minResult(min).maxResult(max).averageResult(average)
                .createdAt(LocalDateTime.now().truncatedTo(
                        ChronoUnit.MINUTES)).build();
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
