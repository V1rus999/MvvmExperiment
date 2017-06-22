package com.droidit.domain.rx_mvvm;

import com.droidit.domain.StateListener;
import com.droidit.domain.rx_java.NorrisJokeDto;
import com.droidit.domain.rx_mvvm.jokes.RxJokeService;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by johannesC on 2017/06/22.
 */

public class DefaultJokeViewModel implements JokeViewModel {

    private final RxJokeService rxJokeService;
    private StateListener<JokeState> stateListener;
    private JokeState state;

    @Inject
    public DefaultJokeViewModel(RxJokeService rxJokeService) {
        this.rxJokeService = rxJokeService;
        state = new JokeState("", false, JokePossibleStates.NORMAL);
    }

    @Override
    public void attachStateListener(StateListener<JokeState> jokeStatesStateListener) {
        stateListener = jokeStatesStateListener;
        stateListener.onStateChange(state);
    }

    @Override
    public void onJokesButtonClicked() {
        rxJokeService.getJoke()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {

                    }
                })
                .doOnNext(new Consumer<NorrisJokeDto>() {
                    @Override
                    public void accept(@NonNull NorrisJokeDto norrisJokeDto) throws Exception {
                        System.out.println(norrisJokeDto.value);
                    }})
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                    }
                }).subscribe();
    }
}
