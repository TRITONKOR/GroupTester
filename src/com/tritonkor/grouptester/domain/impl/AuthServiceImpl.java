package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.domain.exception.UserAlreadyAuthException;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import org.mindrot.bcrypt.BCrypt;

/**
 * The AuthServiceImpl class is an implementation of the AuthService interface, providing
 * authentication-related functionality in the application.
 */
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private User user;

    /**
     * Constructs a new AuthServiceImpl with the specified UserRepository.
     *
     * @param userRepository the user repository used for user-related operations.
     */
    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user with the given username and password.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return true if authentication is successful.
     * @throws AuthException if authentication fails.
     */
    public boolean authenticate(String username, String password) {
        User foundedUser = userRepository.findByUsername(username)
                .orElseThrow(AuthException::new);

        if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
            throw new AuthException();
        }

        user = foundedUser;
        return true;
    }

    /**
     * Checks if a user is currently authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public boolean isAuthenticated() {
        return user != null;
    }

    /**
     * Gets the authenticated user.
     *
     * @return the authenticated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Logs out the authenticated user.
     *
     * @throws UserAlreadyAuthException if the user is not authenticated.
     */
    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthException("You are not yet authenticated.");
        }
        user = null;
    }
}
