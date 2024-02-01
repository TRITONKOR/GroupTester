package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tritonkor.grouptester.persistence.entity.impl.Report;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the ReportRepository interface using JSON for data storage. Extends the
 * GenericJsonRepository for common JSON repository functionality.
 */
public class ReportJsonRepositoryImpl
        extends GenericJsonRepository<Report>
        implements ReportRepository {

    /**
     * Constructs a new ReportJsonRepositoryImpl instance.
     *
     * @param gson The Gson instance for JSON serialization/deserialization.
     */
    public ReportJsonRepositoryImpl(Gson gson) {
        super(gson, JsonPathFactory.REPORTS.getPath(),
                TypeToken.getParameterized(Set.class, Report.class).getType());
    }

    /**
     * Finds a report by its name.
     *
     * @param reportName The name of the report to search for.
     * @return An optional containing the report if found, otherwise empty.
     */
    @Override
    public Optional<Report> findByName(String reportName) {
        return entities.stream().filter(r -> r.getReportTitle().equals(reportName)).findFirst();
    }

    /**
     * Finds all reports associated with a specific group.
     *
     * @param groupName The name of the group to filter reports.
     * @return A set of reports associated with the specified group.
     */
    @Override
    public Set<Report> findAllByGroupName(String groupName) {
        return entities.stream().filter(r -> r.getGroupName().equals(groupName))
                .collect(Collectors.toSet());
    }

    /**
     * Finds all reports associated with a specific test title.
     *
     * @param testTitle The title of the test to filter reports.
     * @return A set of reports associated with the specified test title.
     */
    @Override
    public Set<Report> findAllByTestTitle(String testTitle) {
        return entities.stream().filter(r -> r.getTestTitle().equals(testTitle))
                .collect(Collectors.toSet());
    }
}
