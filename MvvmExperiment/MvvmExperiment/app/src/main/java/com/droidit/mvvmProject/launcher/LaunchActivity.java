package com.droidit.mvvmProject.launcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.login_mvvm.views.LoginMvvmActivity;
import com.droidit.mvvmProject.rx_mvvm.RxMvvmActivity;
import com.droidit.mvvmProject.rxjava.RxJavaActivity;

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

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.launch_mvvm_btn)
    public void onMvvmBtnClicked() {
        LoginMvvmActivity.start(this);
    }

    @OnClick(R.id.launch_rxjava_btn)
    public void onRxJavaBtnClicked() {
        RxJavaActivity.start(this);
    }

    @OnClick(R.id.launch_rxmvvm_btn)
    public void onRxMvvmBtnClicked() {
        RxMvvmActivity.start(this);
    }
}
