package com.droidit.mvvmProject;

import android.app.Application;

import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.ApplicationModule;
import com.droidit.mvvmProject.dependencyInjection.DaggerApplicationComponent;

/**
 * Created by JohannesC on 30-May-16.
 * Application class. Can create own one and use logic in this class.
 */
public class DefaultApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getMainComponent() {
        return applicationComponent;
    }
}
