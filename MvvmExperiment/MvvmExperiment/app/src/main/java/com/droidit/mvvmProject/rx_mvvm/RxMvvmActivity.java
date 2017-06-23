package com.droidit.mvvmProject.rx_mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droidit.domain.rx_mvvm.JokePossibleStates;
import com.droidit.domain.rx_mvvm.JokeState;
import com.droidit.domain.rx_mvvm.JokeViewModel;
import com.droidit.mvvmProject.DefaultApplication;
import com.droidit.mvvmProject.R;
import com.droidit.mvvmProject.dependencyInjection.ApplicationComponent;
import com.droidit.mvvmProject.dependencyInjection.DaggerRxMvvmComponent;
import com.droidit.mvvmProject.dependencyInjection.RxMvvmComponent;
import com.droidit.mvvmProject.util.TransitionHelper;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RxMvvmActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, RxMvvmActivity.class);
        context.startActivity(starter);
        TransitionHelper.transition(context, TransitionHelper.slideInFromRight());
    }

    @BindView(R.id.rxmvvm_jokes_tv)
    TextView rxmvvmJokesTv;
    @BindView(R.id.rxmvvm_jokes_progressbar)
    ProgressBar rxmvvmJokesProgressbar;
    @BindView(R.id.rxmvvm_jokes_button)
    RelativeLayout rxmvvmJokesButton;
    @BindView(R.id.rxmvvm_result_tv)
    TextView rxmvvmResultTv;

    @BindColor(R.color.red)
    int redColor;

    @BindColor(R.color.grey)
    int busyColor;

    @BindColor(R.color.normal)
    int normalColor;

    @Inject
    JokeViewModel viewModel;

    private Disposable viewModelDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.activity_rx_mvvm);
        ButterKnife.bind(this);

        viewModelDisposable = viewModel.observeState()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        viewModel.onSubscribe();
                    }
                })
                .doOnNext(new Consumer<JokeState>() {
                    @Override
                    public void accept(@NonNull JokeState jokeState) throws Exception {
                        if (!jokeState.resultText.isEmpty())
                            rxmvvmResultTv.append(jokeState.resultText);

                        rxmvvmJokesProgressbar.setVisibility(jokeState.progressBarVisible ? View.VISIBLE : View.INVISIBLE);

                        rxmvvmJokesButton.setBackgroundColor(
                                jokeState.currentState == JokePossibleStates.ERROR ? redColor :
                                        (jokeState.currentState == JokePossibleStates.BUSY ? busyColor : normalColor));
                    }
                }).subscribe();
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
    protected void onDestroy() {
        if (!viewModelDisposable.isDisposed()) {
            viewModelDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        TransitionHelper.transition(this, TransitionHelper.slideInFromLeft());
    }

    @OnClick(R.id.rxmvvm_jokes_button)
    public void onJokesButtonClicked() {
        viewModel.onJokesButtonClicked();
    }
}
