package com.tritonkor.grouptester.domain.observer;


import com.tritonkor.grouptester.domain.impl.TestServiceImpl;

public interface Subject {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(Runnable action, TestServiceImpl testService);
}
