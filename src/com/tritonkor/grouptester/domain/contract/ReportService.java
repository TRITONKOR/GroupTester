package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.ReportAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import java.util.Set;

public interface ReportService extends Service<Report> {

    Set<Report> findAllByGroupName(String  groupName);

    Set<Report> findAllByTestTitle(String testTitle);

    Report add(ReportAddDto reportAddDto);

    void saveGrade(Grade grade);

    void resetGrades();
}
