package com.droidit.domain.login_mvvm.login_main_view;

import com.droidit.domain.StateListener;

import javax.inject.Inject;

/**
 * Created by johannesC on 2017/06/18.
 */

public class LoginMainViewModel implements LoginViewModel {

    private StateListener<LoginMainState> stateListener;
    private LoginMainState loginMainState;

    @Inject
    public LoginMainViewModel() {
        loginMainState = new LoginMainState(LoginMainStates.SERVER);
    }

    @Override
    public void attachStateListener(final StateListener<LoginMainState> stateListener) {
        this.stateListener = stateListener;
        stateListener.onStateChange(loginMainState);
    }

    @Override
    public void onServerViewDone() {
        loginMainState.currentState = LoginMainStates.CREDENTIAL;
        stateListener.onStateChange(loginMainState);
    }

    @Override
    public void onCredentialViewDone() {

    }

    @Override
    public void onBackPressed() {
        if (loginMainState.currentState == LoginMainStates.CREDENTIAL) {
            loginMainState.currentState = LoginMainStates.SERVER;
        } else {
            loginMainState.isFinished = true;
        }
        stateListener.onStateChange(loginMainState);
    }
}
