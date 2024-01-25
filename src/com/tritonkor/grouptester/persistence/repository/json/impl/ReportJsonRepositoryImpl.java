package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
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
    public Set<Report> findAllByGroup(Group group) {
        return entities.stream().filter(r -> r.getGroup().equals(group))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Report> findAllByTest(Test test) {
        return entities.stream().filter(r -> r.getTest().equals(test)).collect(Collectors.toSet());
    }
}
