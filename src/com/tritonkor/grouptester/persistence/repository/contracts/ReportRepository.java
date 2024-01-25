package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Set;

public interface ReportRepository extends Repository<Report> {

    Set<Report> findAllByGroup(Group group);

    Set<Report> findAllByTest(Test test);
}
