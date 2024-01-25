package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.util.Optional;

public interface UserService extends Service<User> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
