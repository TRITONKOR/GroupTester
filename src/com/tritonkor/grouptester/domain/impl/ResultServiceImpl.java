package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

public class ResultServiceImpl extends GenericService<Result> implements ResultService {

    private ResultRepository resultRepository;

    public ResultServiceImpl(ResultRepository resultRepository) {
        super(resultRepository);
        this.resultRepository = resultRepository;
    }

    public void makeResult(String userName, String testTitle, Grade grade) {
        Result result = Result.builder().id(UUID.randomUUID()).ownerUsername(userName).grade(grade)
                .testTitle(testTitle).createdAt(LocalDateTime.now().truncatedTo(
                        ChronoUnit.MINUTES)).build();

        resultRepository.add(result);
    }

    @Override
    public Set<Result> findAllByUsername(String username) {
        return resultRepository.findAllByUsername(username);
    }

    @Override
    public Set<Result> findAllByTestTitle(String testTitle) {
        return resultRepository.findAllByTestTitle(testTitle);
    }

    @Override
    public Result add(ResultAddDto resultAddDto) {
        try {
            var result = Result.builder().id(resultAddDto.getId())
                    .ownerUsername(resultAddDto.getOwnerUsername()).grade(resultAddDto.getGrade())
                    .testTitle(resultAddDto.getTestTitle()).createdAt(resultAddDto.getCreatedAt())
                    .build();
            resultRepository.add(result);
            return result;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving a result: %s"
                    .formatted(e.getMessage()));
        }
    }
}
