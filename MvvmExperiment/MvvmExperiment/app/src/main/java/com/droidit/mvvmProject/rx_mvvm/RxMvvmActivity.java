package com.droidit.mvvmProject.rx_mvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.droidit.domain.StateListener;
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

public class RxMvvmActivity extends AppCompatActivity implements StateListener<JokeState> {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        setContentView(R.layout.activity_rx_mvvm);
        ButterKnife.bind(this);
        viewModel.attachStateListener(this);
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

    @OnClick(R.id.rxmvvm_jokes_button)
    public void onJokesButtonClicked() {
        viewModel.onJokesButtonClicked();
    }

    @Override
    public void onStateChange(JokeState jokeState) {
        if (!jokeState.resultText.isEmpty()) rxmvvmResultTv.append(jokeState.resultText);
        rxmvvmJokesProgressbar.setVisibility(jokeState.progressBarVisible ? View.VISIBLE : View.INVISIBLE);
        rxmvvmJokesButton.setBackgroundColor(
                jokeState.currentState == JokePossibleStates.ERROR ? redColor :
                        (jokeState.currentState == JokePossibleStates.BUSY ? busyColor : normalColor));
    }
}
