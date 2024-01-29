package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportJsonRepositoryImpl
        extends GenericJsonRepository<Report>
        implements ReportRepository {

    public ReportJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.REPORTS.getPath(),
                TypeToken.getParameterized(Set.class, Report.class).getType());
    }

    @Override
    public Optional<Report> findByName(String reportName) {
        return entities.stream().filter(r -> r.getReportTitle().equals(reportName)).findFirst();
    }

    @Override
    public Set<Report> findAllByGroupName(String groupName) {
        return entities.stream().filter(r -> r.getGroupName().equals(groupName))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Report> findAllByTestTitle(String testTitle) {
        return entities.stream().filter(r -> r.getTestTitle().equals(testTitle)).collect(Collectors.toSet());
    }
}
