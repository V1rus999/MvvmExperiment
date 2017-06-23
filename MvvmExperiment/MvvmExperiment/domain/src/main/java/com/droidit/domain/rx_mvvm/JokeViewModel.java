package com.droidit.domain.rx_mvvm;

import io.reactivex.Observable;

/**
 * Created by johannesC on 2017/06/22.
 */

public interface JokeViewModel {

    void onSubscribe();

    Observable<JokeState> observeState();

    void onJokesButtonClicked();
}
