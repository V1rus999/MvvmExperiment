package com.droidit.domain.rx_java.normal_example;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.rx_java.NorrisJokeDto;
import com.droidit.domain.threading.BackgroundExecutor;
import com.droidit.domain.threading.MainThread;

import javax.inject.Inject;

/**
 * Created by johannesC on 2017/06/21.
 */

public class MvpExamplePresenter {

    private final MainThread mainThread;
    private final BackgroundExecutor backgroundExecutor;
    private final NorrisJokeService norrisJokeService;
    private MvpExampleView mvpExampleView;

    @Inject
    public MvpExamplePresenter(MainThread mainThread, BackgroundExecutor backgroundExecutor, NorrisJokeService norrisJokeService) {
        this.mainThread = mainThread;
        this.backgroundExecutor = backgroundExecutor;
        this.norrisJokeService = norrisJokeService;
    }

    public void attachView(MvpExampleView mvpExampleView) {
        this.mvpExampleView = mvpExampleView;
    }

    public void onMvpGetStuffButtonClicked() {
        backgroundExecutor.execute(new Runnable() {
            @Override
            public void run() {
                norrisJokeService.getAJoke(new DefaultCallback<NorrisJokeDto>() {
                    @Override
                    public void onSuccess(final NorrisJokeDto success) {
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                mvpExampleView.displayJoke(success.value);
                            }
                        });
                    }

                    @Override
                    public void onFailure(final Throwable throwable) {
                        mainThread.post(new Runnable() {
                            @Override
                            public void run() {
                                mvpExampleView.displayError(throwable.toString());
                            }
                        });
                    }
                });
            }
        });
    }
}
