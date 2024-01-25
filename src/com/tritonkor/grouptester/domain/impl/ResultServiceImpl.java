package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import java.util.Set;

public class ResultServiceImpl extends GenericService<Result> implements ResultService {

    private ResultRepository resultRepository;

    public ResultServiceImpl(Repository<Result> repository,
            ResultRepository resultRepository) {
        super(repository);
        this.resultRepository = resultRepository;
    }

    @Override
    public Set<Result> findAllByUsername(String username) {
        return resultRepository.findAllByUsername(username);
    }

    @Override
    public Set<Result> findAllByTestTitle(String testTitle) {
        return resultRepository.findAllByTestTitle(testTitle);
    }
}
