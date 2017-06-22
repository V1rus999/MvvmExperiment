package com.droidit.domain.login_mvvm.login_main_view;

import com.droidit.domain.BaseViewModel;
import com.droidit.domain.StateListener;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface LoginViewModel extends BaseViewModel<StateListener<LoginMainState>> {

    void onServerViewDone();

    void onCredentialViewDone();

    void onBackPressed();
}
