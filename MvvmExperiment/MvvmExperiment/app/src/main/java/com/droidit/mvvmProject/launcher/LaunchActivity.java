package com.droidit.mvvmProject.launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.login_mvvm.views.LoginMvvmActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LaunchActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.launch_mvvm_btn)
    public void onMvvmBtnClicked() {
        LoginMvvmActivity.start(this);
        finish();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
