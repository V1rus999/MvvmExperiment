package com.droidit.domain.login_mvvm.credential_server_view;

import com.droidit.domain.login_mvvm.BaseViewModel;

/**
 * Created by johannesC on 2017/06/17.
 */

public interface CredentialViewModel extends BaseViewModel<CredentialState> {

    void onLoginButtonClicked(final String username, final String password);
}
