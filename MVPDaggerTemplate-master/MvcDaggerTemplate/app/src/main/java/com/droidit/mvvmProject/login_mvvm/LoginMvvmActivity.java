package com.droidit.mvvmProject.login_mvvm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerLoginVmmvComponent;
import com.droidit.mvvmProject.dependencyInjection.LoginVmmvComponent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginMvvmActivity extends AppCompatActivity {

    @BindView(R.id.server_view)
    ServerView serverView;

    @BindView(R.id.credential_view)
    CredentialView credentialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initializeInjector();
        ButterKnife.bind(this);
        serverView.attachServerCompletionListener(serverCompletionListener);
        serverView.setVisibility(View.VISIBLE);
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

    private final ServerView.ServerCompletionListener serverCompletionListener = new ServerView.ServerCompletionListener() {
        @Override
        public void onDone() {
            Toast.makeText(LoginMvvmActivity.this, "Coolbeans", Toast.LENGTH_SHORT).show();
            serverView.setVisibility(View.GONE);
            credentialView.setVisibility(View.VISIBLE);
        }
    };
}
