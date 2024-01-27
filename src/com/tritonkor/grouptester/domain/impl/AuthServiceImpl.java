package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.contract.AuthService;
import com.tritonkor.grouptester.domain.exception.AuthException;
import com.tritonkor.grouptester.domain.exception.UserAlreadyAuthException;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import org.mindrot.bcrypt.BCrypt;

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private User user;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean authenticate(String username, String password) {
        User foundedUser = userRepository.findByUsername(username)
                .orElseThrow(AuthException::new);

        if (!BCrypt.checkpw(password, foundedUser.getPassword())) {
            return false;
        }

        user = foundedUser;
        return true;
    }

    public boolean isAuthenticated() { return user != null; }

    public User getUser() {
        return user;
    }

    public void logout() {
        if (user == null) {
            throw new UserAlreadyAuthException("You are not yet authenticated.");
        }
        user = null;
    }
}
