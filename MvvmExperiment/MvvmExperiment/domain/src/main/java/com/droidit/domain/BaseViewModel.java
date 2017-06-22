package com.droidit.domain;

/**
 * Created by JohannesC on 05-Sep-16.
 * Template for Contract based MVC structure. Each module should have a contract which implements this class
 */
public interface BaseViewModel<StateListener> {

    void attachStateListener(StateListener stateListener);

}
