package com.droidit.domain.login_mvvm.login_main_view;

import com.droidit.domain.login_mvvm.LoginStateListener;

import javax.inject.Inject;

/**
 * Created by johannesC on 2017/06/18.
 */

public class LoginMainViewModel implements LoginViewModel {

    private LoginStateListener<LoginMainState> stateListener;
    private LoginMainState loginMainState;

    @Inject
    public LoginMainViewModel() {
        loginMainState = new LoginMainState(LoginMainStates.SERVER);
    }

    @Override
    public void attachStateListener(final LoginStateListener<LoginMainState> stateListener) {
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
