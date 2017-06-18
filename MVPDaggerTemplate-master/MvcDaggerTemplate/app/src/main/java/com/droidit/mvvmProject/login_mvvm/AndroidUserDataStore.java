package com.droidit.mvvmProject.login_mvvm;

import android.content.Context;
import android.content.SharedPreferences;

import com.droidit.domain.login_mvvm.datastore.UserDataStore;

import javax.inject.Inject;

/**
 * Created by johannesC on 2017/06/18.
 */

public class AndroidUserDataStore implements UserDataStore {

    private final SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_KEY = "MvvmExampleSharedPreference";
    private static final String URL_KEY = "url_key";

    @Inject
    public AndroidUserDataStore(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    @Override
    public void setUserUrl(String url) {
        synchronized (sharedPreferences) {
            sharedPreferences.edit().putString(URL_KEY, url).apply();
        }
    }

    @Override
    public String getUserUrl() {
        synchronized (sharedPreferences) {
            return sharedPreferences.getString(URL_KEY, "");
        }
    }
}
