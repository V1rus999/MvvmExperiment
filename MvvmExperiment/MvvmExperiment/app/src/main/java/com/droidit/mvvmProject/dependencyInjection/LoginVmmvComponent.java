package com.droidit.mvvmProject.dependencyInjection;

import com.droidit.mvvmProject.login_mvvm.views.CredentialView;
import com.droidit.mvvmProject.login_mvvm.views.LoginMvvmActivity;
import com.droidit.mvvmProject.login_mvvm.views.ServerView;

import dagger.Component;

/**
 * Created by JohannesC on 05-Sep-16.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {NetworkModule.class, ViewModelModule.class})
public interface LoginVmmvComponent {

    void inject(LoginMvvmActivity basicExampleActivity);

    void inject(ServerView serverView);

    void inject(CredentialView credentialView);

}
