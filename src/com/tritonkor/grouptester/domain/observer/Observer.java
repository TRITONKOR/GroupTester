package com.tritonkor.grouptester.domain.observer;

import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;

public interface Observer {

    void update(String testName);
}
