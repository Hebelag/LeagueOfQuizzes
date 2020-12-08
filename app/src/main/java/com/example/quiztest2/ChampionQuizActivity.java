package com.example.quiztest2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;
/*
TO DO 23.11.2020: Screen Rotation Handle DONE
Pressing Samsung Back Button Handle DONE
Window Destroying App keeps currentLevel but resets score because of OnCreate(); DONE

TO DO 24.11.2020:
First Game Mode: 20 Random Champions
Second Game Mode: Marathon Champions (all Champions with exclusion) -> Do a List of already found champs
Third Game Mode: Time Attack (1 minute with most champs, accuracy is important here, not only score!)
This is a test addendum to push to GitHub

 */
public class ChampionQuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_CHAMPION_TEXT = "keyChampionText";
    private static final String KEY_CURRENT_LEVEL = "keyCurrentLevel";
    private static final String KEY_WRONGS = "keyWrongs";
    private static final String KEY_IMG1 = "keyImg1";
    private static final String KEY_IMG2 = "keyImg2";
    private static final String KEY_IMG3 = "keyImg3";
    private static final String KEY_IMG4 = "keyImg4";


    TextView championText, scoreView;
    ImageButton btnAns1, btnAns2, btnAns3, btnAns4;
    TextView finalScore, finalAccuracy;
    TextView timerView, countDownTimerView, finalTimerView;
    Button returnButton, retryButton, buttonStartQuiz;
    ImageView timerImage;
    LinearLayout gameLayout, postGameLayout, timeAttackLayout;

    final int CHAMPION_COUNT = 152;
    final String prefLevel = "currentLevel";

    long gameTime = 0;
    int currentLevel = 1;
    int champID, score, wrongs, lives = 3;
    long countDownMillis;
    float accuracy;

    String champGameMode;
    String championName;
    String imagePath;
    String timeString;

    String[] buttonChampionsKey = new String[4];
    String[] buttonChampions = new String[4];
    String[] buttonChampionImages = new String[4];
    Set<Integer> championsAnswered = new HashSet<>();

    int maxLevel = 0;
    long startTime = 0;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            gameTime = System.currentTimeMillis() - startTime;
            int seconds = (int) (gameTime / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            timeString = String.format(Locale.getDefault(),"%d:%02d", minutes, seconds);

            timerView.setText(timeString);

            timerHandler.postDelayed(this, 500);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent championQuizIntent = this.getIntent();
        champGameMode = championQuizIntent.getStringExtra(ChampionQuizGameMode.KEY_GAME_MODE);
        buttonChampions[0] = "defaultchampion";
        buttonChampions[1] = "defaultchampion";
        buttonChampions[2] = "defaultchampion";
        buttonChampions[3] = "defaultchampion";


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
        timerView = findViewById(R.id.timerView);
        timerImage = findViewById(R.id.timerImageView);
        gameLayout = findViewById(R.id.gameLayout);
        postGameLayout = findViewById(R.id.postGameLayout);
        timeAttackLayout = findViewById(R.id.timeAttackLayout);
        finalTimerView = findViewById(R.id.tvFinalTime);
        countDownTimerView = findViewById(R.id.countdownView);

        if (champGameMode.equals(ChampionQuizGameMode.MODE_TIME_ATTACK)) {
            timerView.setVisibility(View.INVISIBLE);
            timerImage.setVisibility(View.INVISIBLE);
            timeAttackLayout.setVisibility(View.VISIBLE);
            countDownMillis = championQuizIntent.getLongExtra(ChampionQuizGameMode.KEY_TIME,60000);
            int minutes = (int) (countDownMillis / 1000) / 60;
            int seconds = (int) (countDownMillis / 1000) % 60;
            String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            countDownTimerView.setText(timeFormatted);
            maxLevel = 1000;

        } else {
            timerView.setVisibility(View.VISIBLE);
            timerImage.setVisibility(View.VISIBLE);
            timeAttackLayout.setVisibility(View.GONE);
            if (champGameMode.equals(ChampionQuizGameMode.MODE_MARATHON)){
                maxLevel = CHAMPION_COUNT;
            } else {
                maxLevel = championQuizIntent.getIntExtra(ChampionQuizGameMode.KEY_TRAIN, 20);
            }
        }

        btnAns1.setOnClickListener(this);
        btnAns2.setOnClickListener(this);
        btnAns3.setOnClickListener(this);
        btnAns4.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        retryButton.setOnClickListener(this);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonStartQuiz.getText().toString().equals("STOP")){
                    currentLevel = maxLevel+1;
                    loadLevel();
                }
                if (champGameMode.equals(ChampionQuizGameMode.MODE_ENDLESS)){
                    buttonStartQuiz.setClickable(true);
                    buttonStartQuiz.setText("STOP");
                }else {
                    buttonStartQuiz.setClickable(false);
                }

                buttonStartQuiz = (Button) v;
                if (champGameMode.equals(ChampionQuizGameMode.MODE_TIME_ATTACK)) {

                    startCountDown();
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                }
                loadLevel();
            }
        });

        if(savedInstanceState == null){
            currentLevel = 1;
            score = 0;
            wrongs = 0;
        } else {
            score = savedInstanceState.getInt(KEY_SCORE);
            championName = savedInstanceState.getString(KEY_CHAMPION_TEXT);
            wrongs = savedInstanceState.getInt(KEY_WRONGS);
            currentLevel = savedInstanceState.getInt(KEY_CURRENT_LEVEL);
            buttonChampions[0] = savedInstanceState.getString(KEY_IMG1);
            retrieveChampionImage(buttonChampions[0],1);
            buttonChampions[1] = savedInstanceState.getString(KEY_IMG2);
            retrieveChampionImage(buttonChampions[1],2);
            buttonChampions[2] = savedInstanceState.getString(KEY_IMG3);
            retrieveChampionImage(buttonChampions[2],3);
            buttonChampions[3] = savedInstanceState.getString(KEY_IMG4);
            retrieveChampionImage(buttonChampions[3],4);

        }
    }

    private void startCountDown(){
        CountDownTimer countDownTimer = new CountDownTimer(countDownMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownMillis = Math.round((float) millisUntilFinished / 1000.0f) * 1000;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                countDownMillis = 0;
                updateCountDownText();
                currentLevel = maxLevel + 1;
                loadLevel();
            }
        }.start();
    }

    private void updateCountDownText(){
        int minutes = (int) (countDownMillis / 1000) / 60;
        int seconds = (int) (countDownMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        countDownTimerView.setText(timeFormatted);
        if (countDownMillis < 10000) {
            countDownTimerView.setTextColor(Color.RED);
        } else {
            countDownTimerView.setTextColor(Color.BLACK);
        }
    }

    public void retrieveChampionImage(String chmpImg, int x){
        int imageID = getResources().getIdentifier(chmpImg.toLowerCase(),"drawable",getPackageName());
        switch (x){
            case 1:
                btnAns1.setImageResource(imageID);
                break;
            case 2:
                btnAns2.setImageResource(imageID);
                break;
            case 3:
                btnAns3.setImageResource(imageID);
                break;
            case 4:
                btnAns4.setImageResource(imageID);
                break;
        }

    }

    public void loadLevel(){

        DBHelper db = DBHelper.getInstance(this);
        if (currentLevel <= maxLevel) {
            int championID;
            do{
                championID = (int)(Math.random()*1000%CHAMPION_COUNT);
            } while(championID < 1 || championID > CHAMPION_COUNT || (champGameMode.equals(ChampionQuizGameMode.MODE_MARATHON) ? championsAnswered.contains(championID) : false));
            System.out.println(championID);
            String championKey = db.getChampKeyForChampQuiz(championID);
            String[] champion = db.getChampNameFromKey(championKey);

            champID = championID;

            championName = champion[0];
            imagePath = champion[1];

            championText.setText(championName);

            gameLayout.setVisibility(View.VISIBLE);
            postGameLayout.setVisibility(View.INVISIBLE);

            getChampions(champID);
            setChampionImages();
        } else {
            if (champGameMode.equals(ChampionQuizGameMode.MODE_TIME_ATTACK)) {
                finalTimerView.setVisibility(View.GONE);
                String finalScoreText = "Score: " + score;
                finalScore.setText(finalScoreText);

            } else {
                finalTimerView.setVisibility(View.VISIBLE);
                if (champGameMode.equals(ChampionQuizGameMode.MODE_ENDLESS)){
                    finalScore.setVisibility(View.VISIBLE);
                    String finalScoreText = "Score: " + score;
                    finalScore.setText(finalScoreText);
                }
                else {
                    finalScore.setVisibility(View.GONE);
                }
                timerHandler.removeCallbacks(timerRunnable);
                String finalTimeText = String.format(Locale.getDefault(),"Done in: %s",timeString);
                finalTimerView.setText(finalTimeText);
            }
            accuracy = (float)score / ((float)score + (float)wrongs) * 100;
            String finalAccuracyText = String.format(Locale.getDefault(),"Accuracy: %.1f %%",accuracy);
            finalAccuracy.setText(finalAccuracyText);



            gameLayout.setVisibility(View.INVISIBLE);
            postGameLayout.setVisibility(View.VISIBLE);
        }

    }

    public void getChampions(int selectedChampID){

        DBHelper db = DBHelper.getInstance(this);
        for (int i=0;i<3;i++){
            int championID;
            do {
                championID = (int)(Math.random()*1000 % CHAMPION_COUNT);
            } while (championID == selectedChampID || (championID < 1 || championID > CHAMPION_COUNT));
            if (championID != 0) {
                String championKey = db.getChampKeyForChampQuiz(championID);
                buttonChampionsKey[i] = championKey;
            }
        }

        String championRightKey = db.getChampKeyForChampQuiz(champID);

        buttonChampionsKey[3] = championRightKey;

        shuffleArray(buttonChampionsKey);
        for (int i = 0; i< buttonChampionsKey.length; i++) {
            String[] championArray = db.getChampNameFromKey(buttonChampionsKey[i]);
            buttonChampions[i] = championArray[0];
            buttonChampionImages[i] = championArray[1];
        }

    }

    public static void shuffleArray(String[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void setChampionImages(){
        for (int i=0;i<4;i++){
            switch(i){
                case 0:
                    int imageID1 = getResources().getIdentifier(buttonChampionImages[0].toLowerCase(),"drawable",getPackageName());
                    btnAns1.setImageResource(imageID1);
                    break;
                case 1:
                    int imageID2 = getResources().getIdentifier(buttonChampionImages[1].toLowerCase(),"drawable",getPackageName());
                    btnAns2.setImageResource(imageID2);
                    break;
                case 2:
                    int imageID3 = getResources().getIdentifier(buttonChampionImages[2].toLowerCase(),"drawable",getPackageName());
                    btnAns3.setImageResource(imageID3);
                    break;
                case 3:
                    int imageID4 = getResources().getIdentifier(buttonChampionImages[3].toLowerCase(),"drawable",getPackageName());
                    btnAns4.setImageResource(imageID4);
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAns1:
                if(championText.getText().toString().equalsIgnoreCase(buttonChampions[0])){
                    championsAnswered.add(champID);
                    currentLevel++;
                    score++;
                    String scoreViewText = "Score: " + score;
                    scoreView.setText(scoreViewText);
                    loadLevel();

                } else {
                    lives--;
                    if (lives <= 0){
                        gameOver();
                    }
                    wrongs++;
                    Toast.makeText(getApplicationContext(),"WRONG!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAns2:
                if(championText.getText().toString().equalsIgnoreCase(buttonChampions[1])){
                    championsAnswered.add(champID);
                    currentLevel++;
                    score++;
                    String scoreViewText = "Score: "+ score;
                    scoreView.setText(scoreViewText);
                    loadLevel();
                } else {
                    wrongs++;
                    Toast.makeText(getApplicationContext(),"WRONG!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAns3:
                if(championText.getText().toString().equalsIgnoreCase(buttonChampions[2])){
                    championsAnswered.add(champID);
                    currentLevel++;
                    score++;
                    String scoreViewText = "Score: " + score;
                    scoreView.setText(scoreViewText);
                    loadLevel();
                } else {
                    wrongs++;
                    Toast.makeText(getApplicationContext(),"WRONG!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnAns4:
                if(championText.getText().toString().equalsIgnoreCase(buttonChampions[3])){
                    championsAnswered.add(champID);
                    currentLevel++;
                    score++;
                    String scoreViewText = "Score: " + score;
                    scoreView.setText(scoreViewText);
                    loadLevel();
                } else {
                    wrongs++;
                    Toast.makeText(getApplicationContext(),"WRONG!",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.returnButton:
                SharedPreferences preferencesReturnLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
                SharedPreferences.Editor editorReturn = preferencesReturnLevel.edit();
                editorReturn.putInt(prefLevel, 1);
                editorReturn.apply();
                finish();
                break;
            case R.id.retryButton:
                SharedPreferences preferencesLevel = getSharedPreferences(prefLevel, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencesLevel.edit();
                editor.putInt(prefLevel, 1);
                editor.apply();
                String scoreText = "Score: 0";
                scoreView.setText(scoreText);

                String timerText = "0:00";
                timerView.setText(timerText);

                score = 0;
                currentLevel = 1;
                accuracy = 0;
                lives = 0;
                wrongs = 0;
                buttonStartQuiz.setClickable(true);
                int imageID = getResources().getIdentifier("defaultchampion","drawable",getPackageName());
                btnAns1.setImageResource(imageID);
                btnAns2.setImageResource(imageID);
                btnAns3.setImageResource(imageID);
                btnAns4.setImageResource(imageID);

                postGameLayout.setVisibility(View.INVISIBLE);
                gameLayout.setVisibility(View.VISIBLE);


                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    public void gameOver(){
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_WRONGS, wrongs);
        outState.putInt(KEY_CURRENT_LEVEL, currentLevel);
        outState.putString(KEY_CHAMPION_TEXT, championName);
        outState.putString(KEY_IMG1,buttonChampions[0]);
        outState.putString(KEY_IMG2,buttonChampions[1]);
        outState.putString(KEY_IMG3,buttonChampions[2]);
        outState.putString(KEY_IMG4,buttonChampions[3]);
    }

}