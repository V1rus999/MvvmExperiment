package com.droidit.mvvmProject.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.droidit.domain.rx_java.normal_example.MvpExamplePresenter;
import com.droidit.domain.rx_java.normal_example.MvpExampleView;
import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerRxJavaComponent;
import com.droidit.mvvmProject.dependencyInjection.RxJavaComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class RxJavaActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RxJavaActivity.class);
        context.startActivity(starter);
    }

    private Unbinder unbinder;

    @Inject
    MvpExamplePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.activity_rx_java);
        unbinder = ButterKnife.bind(this);
        presenter.attachView(mvpExampleView);
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((DefaultApplication) this.getApplication()).getMainComponent();
    }

    private void initializeInjector() {
        RxJavaComponent component = DaggerRxJavaComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
        component.inject(this);
    }

    @OnClick(R.id.rx_say_hello_btn)
    public void onSayHelloBtnClicked() {
        Observable<String> observable = Observable.just("Hello World!");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @OnClick(R.id.rx_get_stuff)
    public void onRxGetStuffBtnClicked() {

    }


    @OnClick(R.id.mvp_get_stuff)
    public void onNormalGetStuffBtnClicked() {
        presenter.onMvpGetStuffButtonClicked();
    }

    private final MvpExampleView mvpExampleView = new MvpExampleView() {

        @Override
        public void displayJoke(String joke) {
            Toast.makeText(RxJavaActivity.this, joke, Toast.LENGTH_LONG).show();
        }

        @Override
        public void displayError(String error) {
            Toast.makeText(RxJavaActivity.this, error, Toast.LENGTH_LONG).show();
        }
    };

}
