package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.util.Optional;

public class UserServiceImpl extends GenericService<User> implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(Repository<User> repository,
            UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
