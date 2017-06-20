package com.droidit.domain.login_mvvm.login_main_view;

/**
 * Created by johannesC on 2017/06/18.
 */

public class LoginMainState {

    public LoginMainStates currentState;
    public boolean isFinished;

    public LoginMainState(LoginMainStates currentState) {
        this.currentState = currentState;
    }
}
