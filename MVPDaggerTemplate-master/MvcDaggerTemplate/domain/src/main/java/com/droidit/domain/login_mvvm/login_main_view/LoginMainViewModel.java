package com.droidit.domain.login_mvvm.login_main_view;

import com.droidit.domain.login_mvvm.LoginStateListener;

import javax.inject.Inject;

/**
 * Created by johannesC on 2017/06/18.
 */

public class LoginMainViewModel implements LoginViewModel {

    @Inject
    public LoginMainViewModel() {
    }

    @Override
    public void attachStateListener(LoginStateListener<LoginMainState> stateListener) {

    }
}
