package com.droidit.mvvmProject.dependencyInjection;

import com.droidit.domain.authentication.AuthService;
import com.droidit.domain.threading.BackgroundExecutor;
import com.droidit.retrofit.RetrofitAuthenticationService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JohannesC on 01-Jun-16.
 */
@Module
public class NetworkModule {

    @Provides
    public GsonConverterFactory provideGson() {
        return GsonConverterFactory.create();
    }

    @Provides
    public Retrofit.Builder provideApiClient(GsonConverterFactory gsonConverterFactory, BackgroundExecutor backgroundExecutor) {
        return new Retrofit.Builder()
                .callbackExecutor(backgroundExecutor)
                .addConverterFactory(gsonConverterFactory);
    }

    @Provides
    public AuthService provideAuthService(RetrofitAuthenticationService retrofitAuthenticationService) {
        return retrofitAuthenticationService;
    }
}
