package com.droidit.domain.login_mvvm.credential_server_view;

import com.droidit.domain.BaseViewModel;
import com.droidit.domain.StateListener;

/**
 * Created by johannesC on 2017/06/17.
 */

public interface CredentialViewModel extends BaseViewModel<StateListener<CredentialState>> {

    void onLoginButtonClicked(final String username, final String password);

    void onDetached();
}
