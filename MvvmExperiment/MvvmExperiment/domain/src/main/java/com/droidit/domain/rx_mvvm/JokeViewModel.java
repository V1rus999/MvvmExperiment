package com.droidit.domain.rx_mvvm;

import com.droidit.domain.BaseViewModel;
import com.droidit.domain.StateListener;

/**
 * Created by johannesC on 2017/06/22.
 */

public interface JokeViewModel extends BaseViewModel<StateListener<JokeState>> {

    void onJokesButtonClicked();
}
