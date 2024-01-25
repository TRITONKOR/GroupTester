package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ReportService;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.util.Set;

public class ReportServiceImpl extends GenericService<Report> implements ReportService {

    private ReportRepository reportRepository;

    public ReportServiceImpl(Repository<Report> repository,
            ReportRepository reportRepository) {
        super(repository);
        this.reportRepository = reportRepository;
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
