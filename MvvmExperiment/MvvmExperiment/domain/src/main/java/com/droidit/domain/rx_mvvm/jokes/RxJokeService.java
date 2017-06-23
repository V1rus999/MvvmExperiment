package com.droidit.domain.rx_mvvm.jokes;

import com.droidit.domain.rx_java.NorrisJokeDto;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by johannesC on 2017/06/22.
 */

public interface RxJokeService {

    Observable<NorrisJokeDto> getJoke();

    Single<NorrisJokeDto> getJokeSingle();

}
