package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.util.Optional;

public interface UserService extends Service<User> {

    User findByUsername(String username);

    User findByEmail(String email);

    User add(UserAddDto userAddDto);
}
