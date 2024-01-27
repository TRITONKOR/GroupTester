package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import java.util.Optional;

public interface TestService extends Service<Test> {

    Optional<Test> findByTitle(String title);

    Test add(TestAddDto testAddDto);
}
