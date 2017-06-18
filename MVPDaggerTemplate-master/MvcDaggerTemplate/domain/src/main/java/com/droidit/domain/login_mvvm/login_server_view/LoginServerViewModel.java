package com.droidit.domain.login_mvvm.login_server_view;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.DefaultValues;
import com.droidit.domain.authentication.AuthService;
import com.droidit.domain.login_mvvm.LoginStateListener;
import com.droidit.domain.login_mvvm.datastore.UserDataStore;

import javax.inject.Inject;

import static com.droidit.domain.login_mvvm.LoginStates.BUSY;
import static com.droidit.domain.login_mvvm.LoginStates.ERROR;
import static com.droidit.domain.login_mvvm.LoginStates.NORMAL;
import static com.droidit.domain.login_mvvm.LoginStates.SUCCESS;

/**
 * Created by johannesC on 2017/06/15.
 */

public class LoginServerViewModel implements ServerViewModel {

    private final AuthService authService;
    private final UserDataStore userDataStore;
    private LoginStateListener<ServerState> loginStateListener;
    private ServerState serverState;

    @Inject
    public LoginServerViewModel(final AuthService authService, final UserDataStore userDataStore) {
        this.authService = authService;
        this.userDataStore = userDataStore;
        serverState = new ServerState(DefaultValues.defaultUrl, "Next", false, NORMAL);
    }

    public void attachStateListener(final LoginStateListener<ServerState> loginStateListener) {
        this.loginStateListener = loginStateListener;
        this.loginStateListener.onStateChange(serverState); //Default State
    }

    @Override
    public void onLoginButtonClicked(final String serverUrlText) {
        if (serverState.currentState == NORMAL) {
            if (serverUrlText.isEmpty()) {
                switchToErrorState("Empty Server Url");
            } else {
                switchToBusyState();
                checkUrl(serverUrlText);
            }
            return;
        }

        if (serverState.currentState == BUSY || serverState.currentState == ERROR) {
            switchToNormalState();
        }
    }

    private void checkUrl(final String serverUrl) {
        authService.checkUrl(serverUrl, new DefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                switchToSuccessState();
                userDataStore.setUserUrl(serverUrl);
            }

            @Override
            public void onFailure(Throwable throwable) {
                switchToErrorState(throwable.getMessage());
            }
        });
    }

    private void switchToBusyState() {
        serverState.progressBarVisible = true;
        serverState.loginButtonText = "Cancel";
        serverState.currentState = BUSY;
        this.loginStateListener.onStateChange(serverState);
    }

    private void switchToNormalState() {
        serverState.progressBarVisible = false;
        serverState.loginButtonText = "Next";
        serverState.currentState = NORMAL;
        this.loginStateListener.onStateChange(serverState);
    }

    private void switchToErrorState(final String message) {
        serverState.progressBarVisible = false;
        serverState.loginButtonText = message;
        serverState.currentState = ERROR;
        this.loginStateListener.onStateChange(serverState);
    }

    private void switchToSuccessState() {
        serverState.progressBarVisible = false;
        serverState.loginButtonText = "Success!";
        serverState.currentState = SUCCESS;
        serverState.isFinished = true;
        this.loginStateListener.onStateChange(serverState);
    }
}
