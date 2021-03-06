package com.droidit.mvvmProject.login_mvvm.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.droidit.domain.StateListener;
import com.droidit.domain.login_mvvm.ViewCompletionListener;
import com.droidit.domain.login_mvvm.login_main_view.LoginMainState;
import com.droidit.domain.login_mvvm.login_main_view.LoginMainStates;
import com.droidit.domain.login_mvvm.login_main_view.LoginViewModel;
import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerLoginVmmvComponent;
import com.droidit.mvvmProject.dependencyInjection.LoginVmmvComponent;
import com.droidit.mvvmProject.util.TransitionHelper;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MvvmActivity extends AppCompatActivity implements StateListener<LoginMainState> {

    public static void start(Context context) {
        Intent starter = new Intent(context, MvvmActivity.class);
        context.startActivity(starter);
        TransitionHelper.transition(context, TransitionHelper.slideInFromRight());
    }

    @BindView(R.id.server_view)
    ServerView serverView;

    @BindView(R.id.credential_view)
    CredentialView credentialView;

    @Inject
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initializeInjector();
        ButterKnife.bind(this);
        loginViewModel.attachStateListener(this);
        serverView.attachServerCompletionListener(serverCompletionListener);
        serverView.setVisibility(View.VISIBLE);
        credentialView.attachCompletionListener(credentialCompletionListener);
        credentialView.setVisibility(View.GONE);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((DefaultApplication) this.getApplication()).getMainComponent();
    }

    private void initializeInjector() {
        LoginVmmvComponent basicExampleComponent = DaggerLoginVmmvComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
        basicExampleComponent.inject(this);
    }

    @Override
    public void finish() {
        super.finish();
        TransitionHelper.transition(this, TransitionHelper.slideInFromLeft());
    }

    private final ViewCompletionListener serverCompletionListener = new ViewCompletionListener() {
        @Override
        public void onDone() {
            loginViewModel.onServerViewDone();
        }
    };

    private final ViewCompletionListener credentialCompletionListener = new ViewCompletionListener() {
        @Override
        public void onDone() {
            loginViewModel.onCredentialViewDone();
        }
    };

    @Override
    public void onStateChange(final LoginMainState serverLoginState) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (serverLoginState.currentState == LoginMainStates.SERVER)
                    showServer();
                else
                    showCredential();

                if (serverLoginState.isFinished) {
                    MvvmActivity.this.finish();
                }
            }
        });
    }

    private void showServer() {
        if (serverView.getVisibility() != View.VISIBLE) {
            ViewAnimator.animate(credentialView).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                }
            }).duration(200).scale(1, 0).fadeOut().thenAnimate(serverView).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                    credentialView.setVisibility(View.GONE);
                    serverView.setVisibility(View.VISIBLE);
                }
            }).duration(200).scale(0, 1).fadeIn().accelerate().start();
        }
    }

    private void showCredential() {
        if (credentialView.getVisibility() != View.VISIBLE) {
            ViewAnimator.animate(serverView).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                }
            }).duration(200).scale(1, 0).fadeOut().thenAnimate(credentialView).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                    serverView.setVisibility(View.GONE);
                    credentialView.setVisibility(View.VISIBLE);
                }
            }).duration(200).scale(0, 1).fadeIn().accelerate().start();
        }
    }

    @Override
    public void onBackPressed() {
        loginViewModel.onBackPressed();
    }
}
