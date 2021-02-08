package com.scp.leagueofquiz.entrypoint.championquiz;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.common.util.concurrent.ListenableFuture;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import com.scp.leagueofquiz.repository.ChampionRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import timber.log.Timber;

@HiltViewModel
public class ChampionQuizViewModel extends ViewModel {
  private static final Duration TIMER_REFRESH_DELAY = Duration.ofMillis(500);

  // Static data
  private QuizMode quizMode;
  private Integer championCount;
  private final Set<Champion> championsAnswered;

  // Mutable data
  private final MutableLiveData<List<Champion>> championGrid;
  private final MutableLiveData<Integer> score;
  private final MutableLiveData<Integer> failedAttempts;
  private final MutableLiveData<Instant> startTime;
  private final MutableLiveData<Duration> timer;
  private final MutableLiveData<Champion> rightChampion;
  private final MutableLiveData<Boolean> quizFinished;

  // Dependencies
  private final ChampionRepository championRepository;
  private final Executor executor;

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
  public ChampionQuizViewModel(ChampionRepository championRepository, Executor executor) {
    // Initialisations to be removed with dependency injection
    this.championRepository = championRepository;
    this.executor = executor;

    startTime = new MutableLiveData<>();
    championGrid = new MutableLiveData<>();
    resetChampionGrid();
    timer = new MutableLiveData<>(Duration.ZERO);
    championsAnswered = new HashSet<>();
    score = new MutableLiveData<>(0);
    failedAttempts = new MutableLiveData<>(0);
    rightChampion = new MutableLiveData<>();
    quizFinished = new MutableLiveData<>(false);
  }

  private void resetChampionGrid() {
    championGrid.setValue(
        Arrays.asList(Champion.DEFAULT, Champion.DEFAULT, Champion.DEFAULT, Champion.DEFAULT));
  }

  public void startQuiz() {
    startTime.setValue(Instant.now());
    championsAnswered.clear();
    score.setValue(0);
    loadChampionGrid();
    timerHandler.postDelayed(timerRunnableTraining, 0);
  }

  public void pickAnswer(int champIndex) {
    Champion championChosen = championGrid.getValue().get(champIndex - 1);
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
    ListenableFuture<List<Champion>> future =
        championRepository.getRandomChampions(championsAnswered, 4);
    future.addListener(
        () -> {
          List<Champion> randomChampions;
          try {
            randomChampions = future.get();
            rightChampion.postValue(selectRightChampion(randomChampions));
            championGrid.postValue(randomChampions);
          } catch (ExecutionException | InterruptedException e) {
            Timber.e(e, "Could not load champion grid");
            resetChampionGrid();
            rightChampion.postValue(Champion.DEFAULT);
          }
        },
        executor);
  }

  private Champion selectRightChampion(List<Champion> buttonChampions) {
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

  public MutableLiveData<List<Champion>> getChampionGrid() {
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

  public MutableLiveData<Champion> getRightChampion() {
    return rightChampion;
  }

  public MutableLiveData<Boolean> getQuizFinished() {
    return quizFinished;
  }

  public MutableLiveData<Integer> getFailedAttempts() {
    return failedAttempts;
  }
}
