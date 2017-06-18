package com.droidit.domain.login_mvvm.credential_server_view;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.DefaultValues;
import com.droidit.domain.authentication.AuthService;
import com.droidit.domain.login_mvvm.LoginStateListener;

import javax.inject.Inject;

import static com.droidit.domain.login_mvvm.LoginStates.BUSY;
import static com.droidit.domain.login_mvvm.LoginStates.ERROR;
import static com.droidit.domain.login_mvvm.LoginStates.NORMAL;
import static com.droidit.domain.login_mvvm.LoginStates.SUCCESS;

/**
 * Created by johannesC on 2017/06/17.
 */

public class LoginCredentialViewModel implements CredentialViewModel {

    private final AuthService authService;
    private CredentialState credentialState;
    private LoginStateListener<CredentialState> stateListener;

    @Inject
    public LoginCredentialViewModel(final AuthService authService) {
        this.authService = authService;
        credentialState = new CredentialState("Login", false, NORMAL);
    }

    @Override
    public void attachStateListener(LoginStateListener<CredentialState> stateListener) {
        this.stateListener = stateListener;
        stateListener.onStateChange(credentialState);
    }

    @Override
    public void onLoginButtonClicked(final String username, final String password) {
        if (credentialState.currentState == NORMAL) {
            if (username.isEmpty() || password.isEmpty()) {
                switchToErrorState("Empty Credentials");
            } else {
                switchToBusyState();
                authenticate(DefaultValues.defaultUrl, username, password);
            }
            return;
        }

        if (credentialState.currentState == BUSY || credentialState.currentState == ERROR) {
            switchToNormalState();
        }
    }

    private void authenticate(String url, String username, String password) {
        authService.authenticateUrl(url, username, password, new DefaultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                switchToSuccessState();
            }

            @Override
            public void onFailure(Throwable throwable) {
                switchToErrorState(throwable.getMessage());
            }
        });
    }

    private void switchToBusyState() {
        credentialState.progressBarVisible = true;
        credentialState.loginButtonText = "Cancel";
        credentialState.currentState = BUSY;
        this.stateListener.onStateChange(credentialState);
    }

    private void switchToNormalState() {
        credentialState.progressBarVisible = false;
        credentialState.loginButtonText = "Login";
        credentialState.currentState = NORMAL;
        this.stateListener.onStateChange(credentialState);
    }

    private void switchToErrorState(final String message) {
        credentialState.progressBarVisible = false;
        credentialState.loginButtonText = message;
        credentialState.currentState = ERROR;
        this.stateListener.onStateChange(credentialState);
    }

    private void switchToSuccessState() {
        credentialState.progressBarVisible = false;
        credentialState.loginButtonText = "Success!";
        credentialState.currentState = SUCCESS;
        credentialState.isFinished = true;
        this.stateListener.onStateChange(credentialState);
    }
}
