package com.droidit.domain.login_mvvm;

/**
 * Created by JohannesC on 05-Sep-16.
 * Template for Contract based MVC structure. Each module should have a contract which implements this class
 */
public interface BaseViewModel<T> {

    void attachStateListener(final LoginStateListener<T> stateListener);

}
