package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.EntityNotFoundException;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.Repository;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.apache.commons.lang3.NotImplementedException;
import org.mindrot.bcrypt.BCrypt;

public class UserServiceImpl extends GenericService<User> implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> getAll() {
        return getAll(u -> true);
    }

    @Override
    public Set<User> getAll(Predicate<User> filter) {
        return new TreeSet<>(userRepository.findAll(filter));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Даного користувача не існує."));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Даного користувача не існує."));
    }

    @Override
    public User add(User entity) {
        throw new NotImplementedException(
                "Помилка архітектури, так як ми не використовували DTO та маппінг. "
                        + "Прошу використовувати User add(UserAddDto userAddDto) версію.");
    }

    @Override
    public User add(UserAddDto userAddDto) {
        try {
            var user = User.builder().id(userAddDto.getId()).username(userAddDto.username())
                    .email(userAddDto.email())
                    .password(BCrypt.hashpw(userAddDto.rawPassword(), BCrypt.gensalt()))
                    .birthday(userAddDto.birthday()).role(userAddDto.role()).build();
            userRepository.add(user);
            return user;
        } catch (RuntimeException e) {
            throw new SignUpException("Error when saving user: %s"
                    .formatted(e.getMessage()));
        }
    }
}
