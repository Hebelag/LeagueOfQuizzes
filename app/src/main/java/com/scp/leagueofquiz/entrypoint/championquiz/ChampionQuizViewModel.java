package com.scp.leagueofquiz.entrypoint.championquiz;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.common.util.concurrent.ListenableFuture;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.entrypoint.shared.DefaultedLiveData;
import com.scp.leagueofquiz.entrypoint.shared.IncrementableLiveData;
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
  private final IncrementableLiveData score;
  private final IncrementableLiveData failedAttempts;
  private final MutableLiveData<Instant> startTime;
  private final DefaultedLiveData<Duration> timerNonTime;
  private final MutableLiveData<Champion> rightChampion;
  private final MutableLiveData<Boolean> quizFinished;
  private final MutableLiveData<String> buttonText;

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
              timerNonTime.setValue(Duration.between(startTime.getValue(), Instant.now()));
              break;
            case MARATHON:
              timerNonTime.setValue(Duration.between(startTime.getValue(), Instant.now()));
              break;
            case TIME:
              timerNonTime.setValue(timerNonTime.getValue().minus(TIMER_REFRESH_DELAY));
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
    timerNonTime = new DefaultedLiveData<>(Duration.ZERO);
    championsAnswered = new HashSet<>();
    score = new IncrementableLiveData(0);
    failedAttempts = new IncrementableLiveData(0);
    rightChampion = new MutableLiveData<>();
    quizFinished = new MutableLiveData<>(false);
    buttonText = new MutableLiveData<>();
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
    List<Champion> grid = championGrid.getValue();
    if (grid == null) throw new RuntimeException("Grid is empty!");
    Champion championChosen = grid.get(champIndex - 1);
    if (championChosen.equals(rightChampion.getValue())) {
      championsAnswered.add(championChosen);
      score.setIncrement();

      switch (quizMode) {
        case TRAINING:
          if (championsAnswered.size() == championCount) {
            quizFinished.setValue(true);
          } else {
            loadChampionGrid();
          }
          break;
        case ENDLESS:
          // TODO: WEITER ENDLESS IMPLEMENTIEREN
          loadChampionGrid();
          break;
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
      failedAttempts.setIncrement();
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

  public DefaultedLiveData<Duration> getTimerNonTime() {
    return timerNonTime;
  }

  public IncrementableLiveData getScore() {
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

  public IncrementableLiveData getFailedAttempts() {
    return failedAttempts;
  }

  public MutableLiveData<String> getButtonText() {
    return buttonText;
  }
}
