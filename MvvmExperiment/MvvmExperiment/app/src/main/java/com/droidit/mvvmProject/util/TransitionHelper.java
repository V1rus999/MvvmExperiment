package com.droidit.mvvmProject.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.droidit.mvvmProject.R;

/**
 * Created by johannesC on 2017/06/22.
 */

public class TransitionHelper {

    public static void transition(final @NonNull Context context, final @NonNull Pair<Integer, Integer> transition) {
        if (!(context instanceof Activity)) {
            return;
        }

        final Activity activity = (Activity) context;
        activity.overridePendingTransition(transition.first, transition.second);
    }

    public static Pair<Integer, Integer> slideInFromRight() {
        return Pair.create(R.anim.slide_in_right, R.anim.fade_out_slide_out_left);
    }

    public static Pair<Integer, Integer> slideInFromLeft() {
        return Pair.create(R.anim.fade_in_slide_in_left, R.anim.slide_out_right);
    }


}
