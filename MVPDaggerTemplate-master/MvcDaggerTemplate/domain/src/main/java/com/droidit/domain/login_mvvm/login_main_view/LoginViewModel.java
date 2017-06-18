package com.droidit.domain.login_mvvm.login_main_view;

import com.droidit.domain.login_mvvm.BaseViewModel;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface LoginViewModel extends BaseViewModel<LoginMainState> {

    void onServerViewDone();

    void onCredentialViewDone();

    void onBackPressed();
}
