package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface ResultRepository extends Repository<Result> {

    Set<Result> findAllByUsername(String username);

    Set<Result> findAllByTestTitle(String testTitle);

    Optional<Result> findByName(String resultNAme);
}
