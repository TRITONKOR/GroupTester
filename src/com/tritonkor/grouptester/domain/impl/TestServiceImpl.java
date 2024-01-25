package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.TestService;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.Optional;

public class TestServiceImpl
        extends GenericService<Test>
        implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(Repository<Test> repository,
            TestRepository testRepository) {
        super(repository);
        this.testRepository = testRepository;
    }

    @Override
    public Optional<Test> findByTitle(String title) {
        return testRepository.findByTitle(title);
    }
}
