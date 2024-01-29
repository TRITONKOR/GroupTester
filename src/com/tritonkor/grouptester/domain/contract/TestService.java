package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.TestAddDto;
import com.tritonkor.grouptester.domain.impl.ResultServiceImpl;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface TestService extends Service<Test> {

    Test findByTitle(String title);

    Test add(TestAddDto testAddDto);

    Grade runTest(Test test) throws IOException;
}
