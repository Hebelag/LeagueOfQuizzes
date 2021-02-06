package com.scp.leagueofquiz.entrypoint.quizresult;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scp.leagueofquiz.entrypoint.shared.QuizMode;

import java.time.Duration;

public class QuizResultViewModel extends AndroidViewModel {

    @SuppressLint("StaticFieldLeak")
    private final Context applicationContext;

    private QuizMode quizMode;



    private final MutableLiveData<Integer> score;
    private final MutableLiveData<Integer> failedAttempts;
    private final MutableLiveData<Duration> timer;

    public QuizResultViewModel(@NonNull Application application){
        super(application);
        applicationContext = application.getApplicationContext();
        score = new MutableLiveData<>(0);
        failedAttempts = new MutableLiveData<>(0);
        timer = new MutableLiveData<>(Duration.ZERO);
    }
  // TODO: Implement the ViewModel


    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public MutableLiveData<Integer> getFailedAttempts() {
        return failedAttempts;
    }

    public MutableLiveData<Duration> getTimer() {
        return timer;
    }

    public QuizMode getQuizMode() {
        return quizMode;
    }

    public void setQuizMode(QuizMode quizMode) {
        this.quizMode = quizMode;
    }
}
