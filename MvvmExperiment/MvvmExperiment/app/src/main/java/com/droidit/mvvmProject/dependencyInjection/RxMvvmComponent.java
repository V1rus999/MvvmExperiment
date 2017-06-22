package com.droidit.mvvmProject.dependencyInjection;

import com.droidit.mvvmProject.rx_mvvm.RxMvvmActivity;

import dagger.Component;

/**
 * Created by johannesC on 2017/06/22.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {NetworkModule.class, ViewModelModule.class})
public interface RxMvvmComponent {

    void inject(RxMvvmActivity activity);

}