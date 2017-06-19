package com.droidit.domain.login_mvvm.credential_server_view;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.DefaultValues;
import com.droidit.domain.authentication.AuthService;
import com.droidit.domain.login_mvvm.LoginStateListener;
import com.droidit.domain.login_mvvm.datastore.UserDataStore;
import com.droidit.domain.threading.BackgroundExecutor;

import javax.inject.Inject;

import static com.droidit.domain.login_mvvm.LoginStates.BUSY;
import static com.droidit.domain.login_mvvm.LoginStates.ERROR;
import static com.droidit.domain.login_mvvm.LoginStates.NORMAL;
import static com.droidit.domain.login_mvvm.LoginStates.SUCCESS;

/**
 * Created by johannesC on 2017/06/17.
 */

public class LoginCredentialViewModel implements CredentialViewModel {

    private final BackgroundExecutor backgroundExecutor;
    private final AuthService authService;
    private final UserDataStore userDataStore;
    private CredentialState credentialState;
    private LoginStateListener<CredentialState> stateListener;

    @Inject
    public LoginCredentialViewModel(final BackgroundExecutor backgroundExecutor, final UserDataStore userDataStore, final AuthService authService) {
        this.backgroundExecutor = backgroundExecutor;
        this.authService = authService;
        this.userDataStore = userDataStore;
        credentialState = new CredentialState("Login", false, NORMAL);
    }

    @Override
    public void attachStateListener(LoginStateListener<CredentialState> stateListener) {
        this.stateListener = stateListener;
        stateListener.onStateChange(credentialState);
    }

    @Override
    public void onLoginButtonClicked(final String username, final String password) {
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                if (credentialState.currentState == NORMAL) {
                    if (username.isEmpty() || password.isEmpty()) {
                        switchToErrorState("Empty Credentials");
                    } else {
                        switchToBusyState();
                        authenticate(username, password);
                    }
                    return;
                }

                if (credentialState.currentState == BUSY || credentialState.currentState == ERROR) {
                    switchToNormalState();
                }
            }
        });
    }

    @Override
    public void onDetached() {
        credentialState.isFinished = false;
        switchToNormalState();
    }

    private void authenticate(String username, String password) {
        String url = userDataStore.getUserUrl();
        url = url.isEmpty() ? DefaultValues.defaultUrl : url;
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
