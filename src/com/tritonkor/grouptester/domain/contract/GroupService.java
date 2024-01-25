package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import java.util.Optional;

public interface GroupService extends Service<Group> {

    Optional<Group> findByName(String name);
}
