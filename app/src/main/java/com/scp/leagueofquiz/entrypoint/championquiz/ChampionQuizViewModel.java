package com.scp.leagueofquiz.entrypoint.championquiz;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quiztest2.championQuizActivities.ChampionQuizLogic;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import com.scp.leagueofquiz.repository.ChampionRepository;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ChampionQuizViewModel extends ViewModel {
  private static final Duration TIMER_REFRESH_DELAY = Duration.ofMillis(500);

  // Static data
  private QuizMode quizMode;
  private Integer championCount;
  private final Set<QuizChampion> championsAnswered;

  // Mutable data
  private final MutableLiveData<List<QuizChampion>> championGrid;
  private final MutableLiveData<Integer> score;
  private final MutableLiveData<Integer> failedAttempts;
  private final MutableLiveData<Instant> startTime;
  private final MutableLiveData<Duration> timer;
  private final MutableLiveData<QuizChampion> rightChampion;
  private final MutableLiveData<Boolean> quizFinished;

  // Dependencies
  private final ChampionRepository championRepository;

  // Other
  private final Handler timerHandler = new Handler(Looper.getMainLooper());
  private final Runnable timerRunnableTraining =
      new Runnable() {
        @Override
        public void run() {
          switch (quizMode) {
            case TRAINING:
            case ENDLESS:
            case MARATHON:
              timer.setValue(Duration.between(startTime.getValue(), Instant.now()));
              break;
            case TIME:
              timer.setValue(timer.getValue().minus(TIMER_REFRESH_DELAY));
              break;
          }
          timerHandler.postDelayed(this, TIMER_REFRESH_DELAY.toMillis());
        }
      };

  @Inject
  public ChampionQuizViewModel(ChampionRepository championRepository) {
    // Initialisations to be removed with dependency injection
    this.championRepository = championRepository;

    startTime = new MutableLiveData<>();
    championGrid =
        new MutableLiveData<>(
            Arrays.asList(
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT));
    timer = new MutableLiveData<>(Duration.ZERO);
    championsAnswered = new HashSet<>();
    score = new MutableLiveData<>(0);
    failedAttempts = new MutableLiveData<>(0);
    rightChampion = new MutableLiveData<>();
    quizFinished = new MutableLiveData<>(false);
  }

  public void startQuiz() {
    startTime.setValue(Instant.now());
    championsAnswered.clear();
    score.setValue(0);
    loadChampionGrid();
    timerHandler.postDelayed(timerRunnableTraining, 0);
  }

  public void pickAnswer(int champIndex) {
    QuizChampion championChosen = championGrid.getValue().get(champIndex - 1);
    if (championChosen.equals(rightChampion.getValue())) {
      championsAnswered.add(championChosen);
      score.setValue(score.getValue() + 1);

      switch (quizMode) {
        case TRAINING:
        case ENDLESS:
        case MARATHON:
          if (championsAnswered.size() == championCount) {
            quizFinished.setValue(true);
          } else {
            loadChampionGrid();
          }
          break;
        case TIME:
          loadChampionGrid();
          break;
      }

    } else {
      failedAttempts.setValue(failedAttempts.getValue() + 1);
    }
  }

  private void loadChampionGrid() {
    List<QuizChampion> randomChampions =
        championRepository.getRandomChampions(championsAnswered);
    rightChampion.setValue(selectRightChampion(randomChampions));
    championGrid.setValue(randomChampions);
  }

  private QuizChampion selectRightChampion(List<QuizChampion> buttonChampions) {
    int rightChampionPosition = (int) (Math.random() * 4);
    return buttonChampions.get(rightChampionPosition);
  }

  // Accessors
  public QuizMode getQuizMode() {
    return quizMode;
  }

  public void setQuizMode(QuizMode quizMode) {
    this.quizMode = quizMode;
  }

  public MutableLiveData<List<QuizChampion>> getChampionGrid() {
    return championGrid;
  }

  public void setChampionCount(Integer championCount) {
    this.championCount = championCount;
  }

  public MutableLiveData<Duration> getTimer() {
    return timer;
  }

  public MutableLiveData<Integer> getScore() {
    return score;
  }

  public MutableLiveData<Instant> getStartTime() {
    return startTime;
  }

  public MutableLiveData<QuizChampion> getRightChampion() {
    return rightChampion;
  }

  public MutableLiveData<Boolean> getQuizFinished() {
    return quizFinished;
  }

  public MutableLiveData<Integer> getFailedAttempts() {
    return failedAttempts;
  }
}
