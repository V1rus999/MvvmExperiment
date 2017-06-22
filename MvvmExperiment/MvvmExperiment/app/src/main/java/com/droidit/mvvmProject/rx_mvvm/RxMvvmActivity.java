package com.droidit.mvvmProject.rx_mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerRxMvvmComponent;
import com.droidit.mvvmProject.dependencyInjection.RxMvvmComponent;
import com.droidit.mvvmProject.util.TransitionHelper;

public class RxMvvmActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RxMvvmActivity.class);
        context.startActivity(starter);
        TransitionHelper.transition(context, TransitionHelper.slideInFromRight());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.activity_rx_mvvm);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((DefaultApplication) this.getApplication()).getMainComponent();
    }

    private void initializeInjector() {
        RxMvvmComponent component = DaggerRxMvvmComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
        component.inject(this);
    }

    @Override
    public void finish() {
        super.finish();
        TransitionHelper.transition(this, TransitionHelper.slideInFromLeft());
    }
}
