package com.example.quiztest2.championQuizActivities;
/*TODO: 20.01.2021: Nebst ChampionQuizTraining noch ChampionQuizTimeAttack
   was theoretisch alles aus dem ChampionQuizTraining aufgreift außer Feinheiten die ich noch
   deutlich hervorheben werde, bspw. der Timer ist bei TimeAttack nicht da aber bei den anderen
   zwei Spielmodi wieder schon. Also könnte man überlegen ob man das nicht irgendwie overridet
   von einer Mutterklasse?
   Problem mit der Mutterklasse: Es gibt bereits AppCompatActivity von dem extended wird.
   Vielleicht dann die Mutterklasse von AppCompatActivity extenden.
*/
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quiztest2.ChampionQuizModeSelect;
import com.scp.leagueofquiz.R;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ChampionQuizTraining extends AppCompatActivity {
  // Every Activity needs his own Intent
  private Intent championQuizIntent;
  private ChampionQuizLogic logicHandler;
  private String[] championArray;
  private final Set<String> championsAnswered = new HashSet<String>();
  private ImageButton btnAns1, btnAns2, btnAns3, btnAns4;
  private Button returnButton, retryButton, buttonStartQuiz;
  private TextView championText, scoreView;
  private TextView finalScore, finalAccuracy;
  private TextView timerView, finalTimerView;
  private ImageView timerImage;
  private LinearLayout gameLayout, postGameLayout;

  private String[] buttonChampionsImages = new String[4];
  private String[] buttonChampionsKey = new String[4];

  private String rightChampionName;

  private String timeString;
  private static final String championDefaultText = "Champion";

  private int currentLevel;
  private int score;
  private int wrongs;
  private int maxLevel;
  private long startTime;
  private float accuracy;

  Handler timerHandler = new Handler();
  Runnable timerRunnable =
      new Runnable() {
        @Override
        public void run() {
          long gameTime = System.currentTimeMillis() - startTime;
          int seconds = (int) (gameTime / 1000);
          int minutes = seconds / 60;
          seconds = seconds % 60;

          timeString = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
          timerView.setText(timeString);
          timerHandler.postDelayed(this, 500);
        }
      };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_champion_quiz_training);
    loadLayout();
    bindOnClickListeners();

    championText.setText(championDefaultText);

    championQuizIntent = this.getIntent();
    logicHandler = new ChampionQuizLogic();
    championArray = logicHandler.initializeEmptyChampions();
    maxLevel = championQuizIntent.getIntExtra(ChampionQuizModeSelect.KEY_TRAIN, 20);
  }

  private void startQuiz(View v) {
    buttonStartQuiz = (Button) v;
    buttonStartQuiz.setClickable(false);
    startTimer();
    loadNewLevel();
  }

  private void bindOnClickListeners() {
    btnAns1.setOnClickListener(createChampionOnClickListener(0));
    btnAns2.setOnClickListener(createChampionOnClickListener(1));
    btnAns3.setOnClickListener(createChampionOnClickListener(2));
    btnAns4.setOnClickListener(createChampionOnClickListener(3));
    returnButton.setOnClickListener(this::goToMainMenu);
    retryButton.setOnClickListener(this::handleRetryButtonClick);
    buttonStartQuiz.setOnClickListener(this::startQuiz);
  }

  private void goToMainMenu(View v) {
    // SharedPreferences preferencesReturnLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
    // SharedPreferences.Editor editorReturn = preferencesReturnLevel.edit();
    // editorReturn.putInt(prefLevel, 1);
    // editorReturn.apply();
    finish();
  }

  private void handleRetryButtonClick(View v) {
    // SharedPreferences preferencesLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
    // SharedPreferences.Editor editor = preferencesLevel.edit();
    // editor.putInt(prefLevel, 1);
    // editor.apply();
    resetGameValues();
    postGameLayout.setVisibility(View.INVISIBLE);
    gameLayout.setVisibility(View.VISIBLE);
  }

  private void resetGameValues() {
    String scoreText = "Score: 0";
    scoreView.setText(scoreText);

    String timerText = "0:00";
    timerView.setText(timerText);

    score = 0;
    currentLevel = 1;
    accuracy = 0;
    wrongs = 0;

    championText.setText(championDefaultText);

    buttonStartQuiz.setClickable(true);

    championArray = logicHandler.initializeEmptyChampions();

    setChampionImageResources(championArray);
  }

  private void setChampionImageResources(String[] championIDArray) {
    for (int i = 0; i < 4; i++) {
      switch (i) {
        case 0:
          btnAns1.setImageResource(
              getResources()
                  .getIdentifier(championIDArray[0].toLowerCase(), "drawable", getPackageName()));
        case 1:
          btnAns2.setImageResource(
              getResources()
                  .getIdentifier(championIDArray[1].toLowerCase(), "drawable", getPackageName()));
        case 2:
          btnAns3.setImageResource(
              getResources()
                  .getIdentifier(championIDArray[2].toLowerCase(), "drawable", getPackageName()));
        case 3:
          btnAns4.setImageResource(
              getResources()
                  .getIdentifier(championIDArray[3].toLowerCase(), "drawable", getPackageName()));
      }
    }
  }

  private View.OnClickListener createChampionOnClickListener(int champion) {
    return (View v) -> {
      if (championText.getText().toString().equalsIgnoreCase(championArray[champion])) {
        rightAnswer();
        loadNewLevel();
      } else {
        wrongs++;
        Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();
      }
    };
  }

  // loadNewLevel will be the same for every ChampionQuiz Game Mode!
  // It loads the new 4 champions, gets the rightChampion Name, sets the UI championText
  // What I really like about this is that championArray can be locally defined in theory!

  private void loadNewLevel() {
    if (currentLevel <= maxLevel) {

      buttonChampionsKey = logicHandler.getChampionKeyArray(this, championsAnswered);
      championArray = logicHandler.getChampionNameArray(this, buttonChampionsKey);
      buttonChampionsImages = logicHandler.getChampionIDArray(this, buttonChampionsKey);

      rightChampionName = logicHandler.selectRightChampion(championArray);
      setChampionImageResources(buttonChampionsImages);

      championText.setText(rightChampionName);
      gameLayout.setVisibility(View.VISIBLE);
      postGameLayout.setVisibility(View.INVISIBLE);
    } else {
      loadFinishScreen();
    }
  }

  private void loadFinishScreen() {
    // Maybe put everything in doAllAccuracy() method?
    accuracy = (float) score / ((float) score + (float) wrongs) * 100;
    String finalAccuracyText = String.format(Locale.getDefault(), "Accuracy: %.1f %%", accuracy);
    finalAccuracy.setText(finalAccuracyText);
    finalScore.setVisibility(View.GONE);
    stopTimer();
    showFinishTime();

    // UI? SO much UI!?!
    gameLayout.setVisibility(View.INVISIBLE);
    postGameLayout.setVisibility(View.VISIBLE);
  }

  private void startTimer() {
    startTime = System.currentTimeMillis();
    timerHandler.postDelayed(timerRunnable, 0);
  }

  private void stopTimer() {
    timerHandler.removeCallbacks(timerRunnable);
  }

  private void showFinishTime() {
    finalTimerView.setVisibility(View.VISIBLE);
    String finalTimeText = String.format(Locale.getDefault(), "Done in %s", timeString);
    finalTimerView.setText(finalTimeText);
  }

  private void rightAnswer() {
    championsAnswered.add(rightChampionName);
    currentLevel++;
    score++;
    String scoreViewText = "Score: " + score;
    scoreView.setText(scoreViewText);
  }

  private void loadLayout() {
    btnAns1 = findViewById(R.id.btnAns1);
    btnAns2 = findViewById(R.id.btnAns2);
    btnAns3 = findViewById(R.id.btnAns3);
    btnAns4 = findViewById(R.id.btnAns4);
    buttonStartQuiz = findViewById(R.id.startCountdownButton);
    championText = findViewById(R.id.championText);
    scoreView = findViewById(R.id.scoreView);
    finalAccuracy = findViewById(R.id.tvFinalAccuracy);
    finalScore = findViewById(R.id.tvFinalScore);
    returnButton = findViewById(R.id.returnButton);
    retryButton = findViewById(R.id.retryButton);
    timerView = findViewById(R.id.wrongsView);
    timerImage = findViewById(R.id.timerImageView);
    gameLayout = findViewById(R.id.gameLayout);
    postGameLayout = findViewById(R.id.postGameLayout);
    finalTimerView = findViewById(R.id.tvFinalTime);
  }
}
