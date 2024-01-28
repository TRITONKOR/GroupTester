package com.tritonkor.grouptester.domain.observer;


import com.tritonkor.grouptester.domain.impl.TestServiceImpl;
import java.util.UUID;

public interface Observer {

    void update(Runnable action, TestServiceImpl testService);
}
