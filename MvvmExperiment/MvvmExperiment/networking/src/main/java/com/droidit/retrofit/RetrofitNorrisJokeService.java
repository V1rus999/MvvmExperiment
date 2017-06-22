package com.droidit.retrofit;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.rx_java.NorrisJokeDto;
import com.droidit.domain.rx_java.normal_example.NorrisJokeService;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by johannesC on 2017/06/21.
 */

public class RetrofitNorrisJokeService implements NorrisJokeService{

    private final NorrisJokeApi norrisJokeApi;

    @Inject
    public RetrofitNorrisJokeService(final NorrisJokeApi norrisJokeApi) {
        this.norrisJokeApi = norrisJokeApi;
    }

    @Override
    public void getAJoke(DefaultCallback<NorrisJokeDto> callback) {
        Call<NorrisJokeDto> call = norrisJokeApi.getNorrisJoke();
        Response<NorrisJokeDto> responseBody;
        try {
            responseBody = call.execute();
        } catch (IOException e) {
            callback.onFailure(e);
            return;
        }

        if (responseBody.isSuccessful()) {
            callback.onSuccess(responseBody.body());
        } else {
            callback.onFailure(new Throwable(responseBody.message()));
        }
    }
}
