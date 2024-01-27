package com.tritonkor.grouptester.domain.contract;

import com.tritonkor.grouptester.persistence.entity.impl.User;

public interface AuthService {

    boolean authenticate(String username, String password);

    boolean isAuthenticated();

    User getUser();

    void logout();
}
