package com.droidit.mvvmProject.dependencyInjection;

import com.droidit.mvvmProject.rxjava.RxJavaActivity;

import dagger.Component;

/**
 * Created by johannesC on 2017/06/21.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {NetworkModule.class})
public interface RxJavaComponent {

    void inject(RxJavaActivity activity);

}
