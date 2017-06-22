package com.droidit.domain.login_mvvm.login_server_view;

import com.droidit.domain.BaseViewModel;
import com.droidit.domain.StateListener;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface ServerViewModel extends BaseViewModel<StateListener<ServerState>> {

    void onLoginButtonClicked(String serverUrlText);

    void onDetached();
}
