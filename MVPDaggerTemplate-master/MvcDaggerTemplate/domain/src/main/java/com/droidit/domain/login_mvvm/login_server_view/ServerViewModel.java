package com.droidit.domain.login_mvvm.login_server_view;

import com.droidit.domain.login_mvvm.BaseViewModel;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface ServerViewModel extends BaseViewModel<ServerState> {

    void onLoginButtonClicked(String serverUrlText);
}