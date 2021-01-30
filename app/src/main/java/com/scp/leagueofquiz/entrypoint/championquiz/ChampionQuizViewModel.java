package com.scp.leagueofquiz.entrypoint.championquiz;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.quiztest2.championQuizActivities.ChampionQuizLogic;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChampionQuizViewModel extends AndroidViewModel {
  private static final Duration TIMER_REFRESH_DELAY = Duration.ofMillis(500);
  private final Context applicationContext;

  // Static data
  private QuizMode quizMode;
  private final Set<QuizChampion> championsAnswered;

  // Mutable data
  private final MutableLiveData<List<QuizChampion>> championGrid;
  private final MutableLiveData<Integer> score;
  private final MutableLiveData<Instant> startTime;
  private final MutableLiveData<Duration> timer;
  private final MutableLiveData<QuizChampion> rightChampion;

  private final MutableLiveData<Integer> championCount;

  // Dependencies
  private final ChampionQuizLogic logicHandler;

  // Other
  private final Handler timerHandler = new Handler();
  private final Runnable timerRunnable =
      new Runnable() {
        @Override
        public void run() {
          timer.setValue(Duration.between(startTime.getValue(), Instant.now()));
          timerHandler.postDelayed(this, TIMER_REFRESH_DELAY.toMillis());
        }
      };

  public ChampionQuizViewModel(@NonNull Application application) {
    super(application);

    applicationContext = application.getApplicationContext();
    logicHandler = new ChampionQuizLogic();

    startTime = new MutableLiveData<>();
    championGrid =
        new MutableLiveData<>(
            Arrays.asList(
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT,
                QuizChampion.DEFAULT));
    championCount = new MutableLiveData<>();
    timer = new MutableLiveData<>();
    championsAnswered = new HashSet<>();
    score = new MutableLiveData<>();
    rightChampion = new MutableLiveData<>();
  }

  public void startQuiz() {
    startTime.setValue(Instant.now());
    championsAnswered.clear();
    score.setValue(0);
    List<QuizChampion> randomChampions =
        logicHandler.getRandomChampions(applicationContext, championsAnswered);
    rightChampion.setValue(logicHandler.selectRightChampion(randomChampions));
    championGrid.setValue(randomChampions);
    timerHandler.postDelayed(timerRunnable, 0);
  }

  public void pickAnswer(int champIndex) {
    QuizChampion championChosen = championGrid.getValue().get(champIndex - 1);
    if (championChosen.equals(rightChampion.getValue())) {
      championsAnswered.add(championChosen);
      // TODO finish stuff to do on right chooce
    } else {
      // TODO write what to do when choise is wrong
    }
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

  public MutableLiveData<Integer> getChampionCount() {
    return championCount;
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
}
