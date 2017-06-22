package com.droidit.mvvmProject.rxjava;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.droidit.domain.rx_java.NorrisJokeDto;
import com.droidit.domain.rx_java.normal_example.MvpExamplePresenter;
import com.droidit.domain.rx_java.normal_example.MvpExampleView;
import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerRxJavaComponent;
import com.droidit.mvvmProject.dependencyInjection.RxJavaComponent;
import com.droidit.mvvmProject.util.TransitionHelper;
import com.droidit.retrofit.NorrisJokeApi;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RxJavaActivity.class);
        context.startActivity(starter);
        TransitionHelper.transition(context, TransitionHelper.slideInFromRight());
    }

    @BindView(R.id.result_tv)
    TextView result_tv;

    private Unbinder unbinder;
    private Disposable disposable;

    @Inject
    MvpExamplePresenter presenter;

    @Inject
    NorrisJokeApi norrisJokeApi;

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
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
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

    @Override
    public void finish() {
        super.finish();
        TransitionHelper.transition(this, TransitionHelper.slideInFromLeft());
    }

    @OnClick(R.id.rx_say_hello_btn)
    public void onSayHelloBtnClicked() {
        Observable<String> observable = Observable.just("Hello World!");
        observable.map(new Function<String, Object>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return "Rx : \n" + s + "\n\n";
            }
        });
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                result_tv.append(s);
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
        disposable = norrisJokeApi.getNorrisJokeRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<NorrisJokeDto, NorrisJokeDto>() {
                    @Override
                    public NorrisJokeDto apply(@NonNull NorrisJokeDto norrisJokeDto) throws Exception {
                        norrisJokeDto.value = "Rx: \n" + norrisJokeDto.value + "\n\n";
                        return norrisJokeDto;
                    }
                }).doOnNext(new Consumer<NorrisJokeDto>() {
                    @Override
                    public void accept(@NonNull NorrisJokeDto norrisJokeDto) throws Exception {
                        result_tv.append(norrisJokeDto.value);
                    }
                }).subscribe();
    }

    @OnClick(R.id.mvp_get_stuff)
    public void onNormalGetStuffBtnClicked() {
        presenter.onMvpGetStuffButtonClicked();
    }

    private final MvpExampleView mvpExampleView = new MvpExampleView() {

        @Override
        public void displayJoke(String joke) {
            result_tv.append("MVP: \n" + joke + "\n\n");
        }

        @Override
        public void displayError(String error) {
            result_tv.append(error + "\n\n");
        }
    };

}
