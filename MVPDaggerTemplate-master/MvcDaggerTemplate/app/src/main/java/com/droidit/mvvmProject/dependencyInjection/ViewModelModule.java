package com.droidit.mvvmProject.dependencyInjection;

import com.droidit.domain.login_mvvm.credential_server_view.CredentialViewModel;
import com.droidit.domain.login_mvvm.credential_server_view.LoginCredentialViewModel;
import com.droidit.domain.login_mvvm.login_server_view.LoginServerViewModel;
import com.droidit.domain.login_mvvm.login_server_view.ServerViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by johannesC on 2017/06/15.
 */
@Module
public class ViewModelModule {

    @Provides
    public ServerViewModel provideServerViewModel(final LoginServerViewModel loginViewModel) {
        return loginViewModel;
    }

    @Provides
    public CredentialViewModel provideCredentialViewModel(final LoginCredentialViewModel loginViewModel) {
        return loginViewModel;
    }
}
