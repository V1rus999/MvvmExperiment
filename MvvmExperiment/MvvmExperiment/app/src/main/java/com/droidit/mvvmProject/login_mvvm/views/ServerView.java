package com.droidit.mvvmProject.login_mvvm.views;

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
import com.droidit.domain.login_mvvm.ViewCompletionListener;
import com.droidit.domain.login_mvvm.login_server_view.ServerState;
import com.droidit.domain.login_mvvm.login_server_view.ServerViewModel;
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

public class ServerView extends LinearLayout implements LoginStateListener<ServerState> {

    @BindView(R.id.server_url_et)
    EditText serverUrlEt;
    @BindView(R.id.server_login_url_button_tv)
    TextView serverLoginUrlButtonTv;
    @BindView(R.id.server_login_progressbar)
    ProgressBar serverLoginProgressbar;
    @BindView(R.id.server_login_url_button)
    RelativeLayout loginUrlButton;

    @Inject
    ServerViewModel loginServerViewModel;

    private ViewCompletionListener mListener;

    public ServerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initializeInjector(context);
        final LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.server_view_layout, this);
        ButterKnife.bind(this);
        loginServerViewModel.attachStateListener(this);
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

    public void attachServerCompletionListener(final ViewCompletionListener listener) {
        mListener = listener;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == GONE) {
            loginServerViewModel.onDetached();
        }
    }

    @Override
    public void onStateChange(final ServerState serverState) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                serverLoginUrlButtonTv.setText(serverState.loginButtonText);
                serverUrlEt.setText(serverState.serverUrlText.isEmpty() ? "" : serverState.serverUrlText);
                serverLoginProgressbar.setVisibility(serverState.progressBarVisible ? View.VISIBLE : View.INVISIBLE);
                loginUrlButton.setBackgroundColor(serverState.getLoginButtonColor());

                if (mListener != null && serverState.isFinished) {
                    mListener.onDone();
                }
            }
        });
    }

    @OnClick(R.id.server_login_url_button)
    public void onLoginButtonCLicked() {
        ViewAnimator.animate(loginUrlButton).pulse().duration(100).start();
        loginServerViewModel.onLoginButtonClicked(serverUrlEt.getText().toString());
    }
}
