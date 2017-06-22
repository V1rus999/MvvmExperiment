package com.droidit.domain;

/**
 * Created by johannesC on 2017/06/15.
 */

public interface StateListener<State> {

    void onStateChange(final State state);
}
