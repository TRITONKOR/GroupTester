package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import java.util.Set;

public interface ReportService extends Service<Report> {

    Set<Report> findAllByGroupName(String  groupName);

    Set<Report> findAllByTestTitle(String testTitle);

    void saveGrade(Grade grade);

    void resetGrades();
}
