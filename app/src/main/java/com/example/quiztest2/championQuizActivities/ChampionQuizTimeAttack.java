package com.example.quiztest2.championQuizActivities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class ChampionQuizTimeAttack extends AppCompatActivity {
  // Every Activity needs his own Intent
  private Intent championQuizIntent;
  private ChampionQuizLogic logicHandler;
  private String[] championArray;
  private final Set<String> championsAnswered = new HashSet<String>();
  private ImageButton btnAns1, btnAns2, btnAns3, btnAns4;
  private Button returnButton, retryButton, buttonStartQuiz;
  private TextView championText, scoreView;
  private TextView finalScore, finalAccuracy;
  private TextView wrongsView, countDownView;
  private LinearLayout gameLayout, postGameLayout;

  private String[] buttonChampionsImages = new String[4];
  private String[] buttonChampionsKey = new String[4];

  private String rightChampionName;

  private int score;
  private int wrongs;
  private long countDownMillis;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_champion_quiz_time_attack);
    championQuizIntent = this.getIntent();
    loadLayout();
    bindOnClickListeners();
    championText.setText(R.string.default_champpick);
    resetCountDownText();
    logicHandler = new ChampionQuizLogic();
    championArray = logicHandler.initializeEmptyChampions();
  }

  private void startQuiz(View v) {
    buttonStartQuiz = (Button) v;
    buttonStartQuiz.setClickable(false);
    startCountDown();
    loadNewLevel();
  }

  private void startCountDown() {
    countDownMillis = championQuizIntent.getLongExtra(ChampionQuizModeSelect.KEY_TIME, 60000);
    countDownView.setTextColor(Color.BLACK);
    CountDownTimer countDownTimer =
        new CountDownTimer(countDownMillis, 100) {
          @Override
          public void onTick(long millisUntilFinished) {
            countDownMillis = Math.round((float) millisUntilFinished / 1000.0f) * 1000;
            updateCountDownText();
          }

          @Override
          public void onFinish() {
            countDownMillis = 0;
            updateCountDownText();
            loadFinishScreen();
          }
        }.start();
  }

  // Connected to startCountDown()
  private void updateCountDownText() {
    int minutes = (int) (countDownMillis / 1000) / 60;
    int seconds = (int) (countDownMillis / 1000) % 60;
    String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    countDownView.setText(timeFormatted);
    if (countDownMillis < 10000) {
      countDownView.setTextColor(Color.RED);
    } else {
      countDownView.setTextColor(Color.BLACK);
    }
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
    scoreView.setText(R.string.score_text);
    countDownView.setText(R.string.countdown_default);
    wrongsView.setText(R.string.wrongs_default);

    score = 0;
    wrongs = 0;

    championText.setText(R.string.default_champpick);

    buttonStartQuiz.setClickable(true);

    championArray = logicHandler.initializeEmptyChampions();

    resetCountDownText();

    setChampionImageResources(championArray);
  }

  private void resetCountDownText() {
    countDownMillis = championQuizIntent.getLongExtra(ChampionQuizModeSelect.KEY_TIME, 60000);
    int minutes = (int) (countDownMillis / 1000) / 60;
    int seconds = (int) (countDownMillis / 1000) % 60;
    String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    countDownView.setText(timeFormatted);
    countDownView.setTextColor(Color.BLACK);
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
        String wrongsViewText = "Wrongs: " + wrongs;
        wrongsView.setText(wrongsViewText);
        Toast.makeText(getApplicationContext(), "WRONG!", Toast.LENGTH_SHORT).show();
      }
    };
  }

  // loadNewLevel will be the same for every ChampionQuiz Game Mode!
  // It loads the new 4 champions, gets the rightChampion Name, sets the UI championText
  // What I really like about this is that championArray can be locally defined in theory!

  private void loadNewLevel() {
    buttonChampionsKey = logicHandler.getChampionKeyArray(this, new HashSet<>());
    championArray = logicHandler.getChampionNameArray(this, buttonChampionsKey);
    buttonChampionsImages = logicHandler.getChampionIDArray(this, buttonChampionsKey);
    rightChampionName = logicHandler.selectRightChampion(championArray);
    setChampionImageResources(buttonChampionsImages);

    championText.setText(rightChampionName);
    gameLayout.setVisibility(View.VISIBLE);
    postGameLayout.setVisibility(View.INVISIBLE);
  }

  private void loadFinishScreen() {
    // Maybe put everything in doAllAccuracy() method?
    float accuracy = (float) score / ((float) score + (float) wrongs) * 100;
    String finalAccuracyText = String.format(Locale.getDefault(), "Accuracy: %.1f %%", accuracy);
    finalAccuracy.setText(finalAccuracyText);
    String finalScoreText = "Score: " + score;
    finalScore.setText(finalScoreText);
    finalScore.setVisibility(View.VISIBLE);

    // UI? SO much UI!?!
    gameLayout.setVisibility(View.INVISIBLE);
    postGameLayout.setVisibility(View.VISIBLE);
  }

  private void rightAnswer() {
    championsAnswered.add(rightChampionName);
    score++;
    String scoreViewText = "Score: " + score;
    scoreView.setText(scoreViewText);
  }

  private void loadLayout() {
    // Needs to be in every ChampionQuiz
    btnAns1 = findViewById(R.id.btnAns1);
    btnAns2 = findViewById(R.id.btnAns2);
    btnAns3 = findViewById(R.id.btnAns3);
    btnAns4 = findViewById(R.id.btnAns4);
    buttonStartQuiz = findViewById(R.id.startQuizButton);
    championText = findViewById(R.id.championText);
    scoreView = findViewById(R.id.scoreViewText);
    finalAccuracy = findViewById(R.id.tvFinalAccuracy);
    returnButton = findViewById(R.id.returnButton);
    retryButton = findViewById(R.id.retryButton);
    gameLayout = findViewById(R.id.gameLayout);
    postGameLayout = findViewById(R.id.postGameLayout);

    finalScore = findViewById(R.id.tvFinalScore);

    wrongsView = findViewById(R.id.wrongsViewText);
    countDownView = findViewById(R.id.countdownView);

    ImageView countDownTimerView = findViewById(R.id.timeAttackTimer);
  }
}
