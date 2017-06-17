package com.droidit.domain.login_mvvm;

/**
 * Created by johannesC on 2017/06/15.
 */

public final class LoginColors {

    public static int getColorForState(LoginStates loginState) {
        if (loginState == null) {
            return 0xff33b5e5;
        }

        switch (loginState) {
            case NORMAL:
                return 0xff33b5e5;
            case BUSY:
                return 0xffcccccc;
            case ERROR:
                return 0xffcc0000;
            default:
                return 0xff33b5e5;


        }
    }
}
