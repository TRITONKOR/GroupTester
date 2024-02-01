package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.ResultService;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

/**
 * The ResultServiceImpl class is an implementation of the ResultService interface, providing
 * functionality related to generating, finding, and managing results in the application.
 */
public class ResultServiceImpl extends GenericService<Result> implements ResultService {

    private ResultRepository resultRepository;

    /**
     * Constructs a new ResultServiceImpl with the specified ResultRepository.
     *
     * @param resultRepository the repository used for result-related operations.
     */
    public ResultServiceImpl(ResultRepository resultRepository) {
        super(resultRepository);
        this.resultRepository = resultRepository;
    }

    /**
     * Generates a new result based on the provided parameters and adds it to the repository.
     *
     * @param userName  the username of the user associated with the result.
     * @param testTitle the title of the test associated with the result.
     * @param grade     the grade achieved in the test.
     */
    public void makeResult(String userName, String testTitle, Grade grade) {
        Result result = Result.builder().id(UUID.randomUUID()).resultTitle("TestService")
                .ownerUsername(userName)
                .testTitle(testTitle).grade(grade).createdAt(LocalDateTime.now().truncatedTo(
                        ChronoUnit.MINUTES)).build();

        resultRepository.add(result);
    }

    /**
     * Finds a result by its name.
     *
     * @param resultName the name of the result to find.
     * @return the found result or null if not found.
     */
    @Override
    public Result findByName(String resultName) {
        return resultRepository.findByName(resultName).orElse(null);
    }

    /**
     * Finds all results associated with a specific username.
     *
     * @param username the username of the user.
     * @return a set of results associated with the specified username.
     */
    @Override
    public Set<Result> findAllByUsername(String username) {
        return resultRepository.findAllByUsername(username);
    }

    /**
     * Finds all results associated with a specific test.
     *
     * @param testTitle the title of the test.
     * @return a set of results associated with the specified test.
     */
    @Override
    public Set<Result> findAllByTestTitle(String testTitle) {
        return resultRepository.findAllByTestTitle(testTitle);
    }

    /**
     * Adds a new result based on the provided ResultAddDto.
     *
     * @param resultAddDto the details of the result to add.
     * @return the added result.
     * @throws SignUpException if there is an error when saving the result.
     */
    @Override
    public Result add(ResultAddDto resultAddDto) {
        try {
            var result = Result.builder().id(resultAddDto.getId())
                    .resultTitle(resultAddDto.getResultTitle())
                    .ownerUsername(resultAddDto.getOwnerUsername())
                    .testTitle(resultAddDto.getTestTitle()).grade(resultAddDto.getGrade())
                    .createdAt(resultAddDto.getCreatedAt())
                    .build();
            resultRepository.add(result);
            return result;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving a result: %s"
                    .formatted(e.getMessage()));
        }
    }
}
