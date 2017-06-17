package com.droidit.mvvmProject.login_mvvm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droidit.domain.login_mvvm.LoginStateListener;
import com.droidit.domain.login_mvvm.credential_server_view.CredentialState;
import com.droidit.domain.login_mvvm.credential_server_view.CredentialViewModel;
import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerLoginVmmvComponent;
import com.droidit.mvvmProject.dependencyInjection.LoginVmmvComponent;
import com.github.florent37.viewanimator.ViewAnimator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by johannesC on 2017/06/15.
 */

public class CredentialView extends LinearLayout implements LoginStateListener<CredentialState> {

    @BindView(R.id.credential_username_et)
    EditText credentialUsernameEt;
    @BindView(R.id.credential_password_et)
    EditText credentialPasswordEt;
    @BindView(R.id.credential_login_url_button_tv)
    TextView credentialLoginUrlButtonTv;
    @BindView(R.id.credential_login_progressbar)
    ProgressBar credentialLoginProgressbar;
    @BindView(R.id.credential_login_button)
    RelativeLayout credentialLoginButton;

    @Inject
    CredentialViewModel credentialViewModel;


    public CredentialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initializeInjector(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.credential_view_layout, this);
        ButterKnife.bind(this);
        credentialViewModel.attachStateListener(this);
    }

    private ApplicationComponent getApplicationComponent(final Context context) {
        return ((DefaultApplication) context.getApplicationContext()).getMainComponent();
    }

    private void initializeInjector(final Context context) {
        final LoginVmmvComponent basicExampleComponent = DaggerLoginVmmvComponent.builder()
                .applicationComponent(getApplicationComponent(context))
                .build();
        basicExampleComponent.inject(this);
    }

    @Override
    public void onStateChange(final CredentialState credentialState) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                credentialLoginUrlButtonTv.setText(credentialState.loginButtonText);
                credentialLoginProgressbar.setVisibility(credentialState.progressBarVisible ? View.VISIBLE : View.INVISIBLE);
                credentialLoginButton.setBackgroundColor(credentialState.getLoginButtonColor());
            }
        });
    }

    @OnClick(R.id.credential_login_button)
    public void onLoginButtonCLicked() {
        ViewAnimator.animate(credentialLoginButton).pulse().duration(100).start();
        credentialViewModel.onLoginButtonClicked(credentialUsernameEt.getText().toString(), credentialPasswordEt.getText().toString());
    }
}