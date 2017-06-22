package com.droidit.domain.rx_java.normal_example;

import com.droidit.domain.DefaultCallback;
import com.droidit.domain.rx_java.NorrisJokeDto;

/**
 * Created by johannesC on 2017/06/21.
 */

public interface NorrisJokeService {

    void getAJoke(DefaultCallback<NorrisJokeDto> callback);

}
