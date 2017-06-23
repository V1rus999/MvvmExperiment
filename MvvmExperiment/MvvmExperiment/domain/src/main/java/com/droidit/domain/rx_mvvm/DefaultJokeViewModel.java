package com.droidit.domain.rx_mvvm;

import com.droidit.domain.rx_java.NorrisJokeDto;
import com.droidit.domain.rx_mvvm.jokes.RxJokeService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by johannesC on 2017/06/22.
 */

public class DefaultJokeViewModel implements JokeViewModel {

    private final RxJokeService rxJokeService;
    private JokeState state;
    private final BehaviorSubject<JokeState> jokeState = BehaviorSubject.create();

    @Inject
    public DefaultJokeViewModel(RxJokeService rxJokeService) {
        this.rxJokeService = rxJokeService;
        state = new JokeState("", false, JokePossibleStates.NORMAL);
    }

    @Override
    public void onSubscribe() {
        jokeState.onNext(state);
    }

    @Override
    public Observable<JokeState> observeState() {
        return jokeState;
    }

    @Override
    public void onJokesButtonClicked() {
        rxJokeService.getJoke()
                .subscribeOn(Schedulers.io())
                .map(new Function<NorrisJokeDto, NorrisJokeDto>() {
                    @Override
                    public NorrisJokeDto apply(@NonNull NorrisJokeDto norrisJokeDto) throws Exception {
                        norrisJokeDto.value = norrisJokeDto.value + "\n\n";
                        return norrisJokeDto;
                    }
                }).subscribe(obs);
    }

    private final Observer<NorrisJokeDto> obs = new Observer<NorrisJokeDto>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            state.resultText = "";
            state.progressBarVisible = true;
            state.currentState = JokePossibleStates.BUSY;
            jokeState.onNext(state);
        }

        @Override
        public void onNext(NorrisJokeDto norrisJokeDto) {
            state.resultText = norrisJokeDto.value;
            state.progressBarVisible = false;
            state.currentState = JokePossibleStates.NORMAL;
            jokeState.onNext(state);
        }

        @Override
        public void onError(Throwable t) {
            state.resultText = t.toString();
            state.progressBarVisible = false;
            state.currentState = JokePossibleStates.ERROR;
            jokeState.onNext(state);
        }

        @Override
        public void onComplete() {

        }
    };
}
