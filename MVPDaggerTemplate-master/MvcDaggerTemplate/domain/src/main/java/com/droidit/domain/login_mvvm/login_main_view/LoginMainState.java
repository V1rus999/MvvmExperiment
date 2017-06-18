package com.droidit.domain.login_mvvm.login_main_view;

/**
 * Created by johannesC on 2017/06/18.
 */

public class LoginMainState {

    public boolean serverViewVisible;
    public boolean credentialViewVisible;

    public LoginMainState(boolean serverViewVisible, boolean credentialViewVisible) {
        this.serverViewVisible = serverViewVisible;
        this.credentialViewVisible = credentialViewVisible;
    }
}
