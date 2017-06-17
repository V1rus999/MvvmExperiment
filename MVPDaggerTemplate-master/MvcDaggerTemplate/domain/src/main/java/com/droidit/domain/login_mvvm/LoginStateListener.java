package com.droidit.domain.login_mvvm;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface LoginStateListener<T> {

    void onStateChange(final T serverLoginState);
}
