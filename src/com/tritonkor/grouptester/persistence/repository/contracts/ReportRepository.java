package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface ReportRepository extends Repository<Report> {

    Optional<Report> findByName(String reportName);

    Set<Report> findAllByGroupName(String groupName);

    Set<Report> findAllByTestTitle(String testTitle);
}
