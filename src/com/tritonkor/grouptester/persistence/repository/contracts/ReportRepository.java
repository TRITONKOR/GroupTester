package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code ReportRepository} interface provides methods for interacting with the repository of
 * {@link Report} entities.
 */
public interface ReportRepository extends Repository<Report> {

    /**
     * Finds a report by its name.
     *
     * @param reportName The name of the report to find.
     * @return An optional containing the report if found, or empty if not found.
     */
    Optional<Report> findByName(String reportName);

    /**
     * Finds all reports associated with a specific group.
     *
     * @param groupName The name of the group for which to find reports.
     * @return A set of reports associated with the specified group.
     */
    Set<Report> findAllByGroupName(String groupName);

    /**
     * Finds all reports associated with a specific test.
     *
     * @param testTitle The title of the test for which to find reports.
     * @return A set of reports associated with the specified test.
     */
    Set<Report> findAllByTestTitle(String testTitle);
}
