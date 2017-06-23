package com.droidit.domain.rx_mvvm;

/**
 * Created by johannesC on 2017/06/22.
 */

public class JokeState {

    public String resultText;
    public boolean progressBarVisible;
    public JokePossibleStates currentState;

    public JokeState(String resultText, boolean progressBarVisible, JokePossibleStates currentState) {
        this.resultText = resultText;
        this.progressBarVisible = progressBarVisible;
        this.currentState = currentState;
    }
}
