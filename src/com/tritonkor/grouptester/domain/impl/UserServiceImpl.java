package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.UserService;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.domain.exception.EntityNotFoundException;
import com.tritonkor.grouptester.domain.exception.SignUpException;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.mindrot.bcrypt.BCrypt;

/**
 * The UserServiceImpl class is an implementation of the UserService interface, providing
 * functionality related to managing user entities in the application.
 */
public class UserServiceImpl extends GenericService<User> implements UserService {

    private UserRepository userRepository;

    /**
     * Constructs a new UserServiceImpl with the specified UserRepository.
     *
     * @param userRepository the repository used for user-related operations.
     */
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users satisfying the given filter.
     *
     * @return a set of all users in the system.
     */
    @Override
    public Set<User> getAll() {
        return getAll(u -> true);
    }

    /**
     * Retrieves all users satisfying the given filter.
     *
     * @param filter the predicate to filter users.
     * @return a set of users satisfying the filter.
     */
    @Override
    public Set<User> getAll(Predicate<User> filter) {
        return new TreeSet<>(userRepository.findAll(filter));
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find.
     * @return the found user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("This user does not exist."));
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user to find.
     * @return the found user.
     * @throws EntityNotFoundException if the user does not exist.
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("This user does not exist."));
    }

    /**
     * Adds a new user based on the provided UserAddDto.
     *
     * @param userAddDto the details of the user to add.
     * @return the added user.
     * @throws SignUpException if there is an error when saving the user.
     */
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
