package com.tritonkor.grouptester.domain.observer;

import com.tritonkor.grouptester.domain.impl.TestServiceImpl;

/**
 * The Subject interface defines a contract for objects that act as subjects in the Observer
 * pattern. Subjects maintain a list of observers and notify them about changes in their state.
 */
public interface Subject {

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer The observer to be added.
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer The observer to be removed.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers about a state change by calling their update method.
     *
     * @param action      The action to be performed by the observers.
     * @param testService The TestServiceImpl instance associated with the state change.
     */
    void notifyObservers(Runnable action, TestServiceImpl testService);
}
