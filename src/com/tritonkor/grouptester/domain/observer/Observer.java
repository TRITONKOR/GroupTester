package com.tritonkor.grouptester.domain.observer;


import com.tritonkor.grouptester.domain.impl.TestServiceImpl;

/**
 * The Observer interface defines a contract for objects that should be notified
 * when there are changes in the state of the observed object.
 */
public interface Observer {

    /**
     * This method is called to notify the observer about a state change in the observed object.
     *
     * @param action      The action to be performed by the observer.
     * @param testService The TestServiceImpl instance associated with the state change.
     */
    void update(Runnable action, TestServiceImpl testService);
}
