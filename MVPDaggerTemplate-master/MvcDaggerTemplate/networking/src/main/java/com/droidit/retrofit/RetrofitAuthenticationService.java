package com.droidit.retrofit;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.authentication.AuthService;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by johannesC on 2017/06/15.
 */

public class RetrofitAuthenticationService implements AuthService {

    private final Retrofit.Builder retrofit;
    private final AuthenticationInterceptor authenticationInterceptor;

    @Inject
    public RetrofitAuthenticationService(Retrofit.Builder retrofit, AuthenticationInterceptor authenticationInterceptor) {
        this.retrofit = retrofit;
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void checkUrl(String url, final DefaultCallback<Boolean> callback) {
        AuthenticationApi authApi = retrofit.baseUrl(url).build().create(AuthenticationApi.class);
        Call<ResponseBody> call = authApi.getUser("current");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() == 401) {
                    callback.onSuccess(true);
                } else {
                    callback.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    @Override
    public void authenticateUrl(final String url, final String username, final String password, final DefaultCallback<Boolean> callback) {
        authenticationInterceptor.addCredentials(username, password);
        AuthenticationApi authApi = retrofit.baseUrl(url)
                .client(new OkHttpClient.Builder().addInterceptor(authenticationInterceptor).build()).build()
                .create(AuthenticationApi.class);
        Call<ResponseBody> call = authApi.getUser("current");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() < 300) {
                    callback.onSuccess(true);
                } else {
                    callback.onFailure(new Throwable(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
