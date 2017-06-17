package com.droidit.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface AuthenticationApi {

    static final String defaultUrl = "";

    @GET("user/{UserName}")
    Call<ResponseBody> getUser(@Path("UserName") final String userName);
}
