package com.droidit.retrofit;

import com.droidit.domain.rx_java.NorrisJokeDto;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by johannesC on 2017/06/21.
 */

public interface NorrisJokeApi {

    @GET("jokes/random")
    Call<NorrisJokeDto> getNorrisJoke();

    @GET("jokes/random")
    Observable<NorrisJokeDto> getNorrisJokeRx();

    @GET("jokes/random")
    Single<NorrisJokeDto> getNorrisJokeRxSingle();
}
