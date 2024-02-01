package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.dto.UserAddDto;
import com.tritonkor.grouptester.persistence.entity.impl.User;

/**
 * The UserService interface defines the contract for managing user-related operations in the
 * application. It provides methods for retrieving users by username or email and adding new users.
 */
public interface UserService extends Service<User> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The found user or null if not found.
     */
    User findByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The found user or null if not found.
     */
    User findByEmail(String email);

    /**
     * Adds a new user based on the provided UserAddDto.
     *
     * @param userAddDto The DTO containing information to create the new user.
     * @return The added user.
     */
    User add(UserAddDto userAddDto);
}
