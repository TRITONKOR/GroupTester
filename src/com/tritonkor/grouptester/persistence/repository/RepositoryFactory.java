package com.tritonkor.grouptester.persistence.repository;

import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import com.tritonkor.grouptester.persistence.repository.json.impl.JsonRepositoryFactory;
import org.apache.commons.lang3.NotImplementedException;

public abstract class RepositoryFactory {

    public static final int JSON = 1;
    public static final int XML = 2;

    public static RepositoryFactory getRepositoryFactory(int whichFactory) {
        return switch (whichFactory) {
            case JSON -> JsonRepositoryFactory.getInstance();
            case XML -> throw new NotImplementedException("Робота з XML файлами не реалізована.");
            default -> throw new IllegalArgumentException(
                    "Помилка при виборі фабрики репозиторіїв.");
        };
    }

    public abstract UserRepository getUserRepository();

    public abstract GroupRepository getGroupRepository();

    public abstract TestRepository getTestRepository();

    public abstract ResultRepository getResultRepository();

    public abstract ReportRepository getReportRepository();

    public abstract void commit();

}
