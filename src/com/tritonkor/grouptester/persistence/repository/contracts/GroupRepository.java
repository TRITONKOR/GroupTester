package com.tritonkor.grouptester.persistence.repository.contracts;

import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Optional;

public interface GroupRepository extends Repository<Group> {

    Optional<Group> findByName(String name);

    Optional<Group> findByUser(User user);
}
