package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.ResultAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.util.Set;

public interface ResultService extends Service<Result> {

    Set<Result> findAllByUsername(String username);

    Set<Result> findAllByTestTitle(String testTitle);

    Result findByName(String resultName);

    Result add(ResultAddDto resultAddDto);
}
