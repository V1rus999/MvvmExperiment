package com.droidit.domain.authentication;

import com.droidit.domain.DefaultCallback;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface AuthService {

    void checkUrl(final String url, final DefaultCallback<Boolean> callback);

    void authenticateUrl(final String url, final String username, final String password, final DefaultCallback<Boolean> callback);
}
