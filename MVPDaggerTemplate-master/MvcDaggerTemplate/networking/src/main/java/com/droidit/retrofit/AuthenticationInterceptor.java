package com.droidit.retrofit;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by johannesC on 2017/06/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String username;
    private String password;

    @Inject
    public AuthenticationInterceptor() {
    }

    public void addCredentials(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", Credentials.basic(username, password)); // <-- this is the important line

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
