package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;
import java.util.Set;

public interface TestRepository extends Repository<Test> {

    Optional<Test> findByTitle(String title);
}
