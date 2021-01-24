package com.example.quiztest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.quiztest2.championQuizActivities.ChampionQuizActivity;
import com.example.quiztest2.championQuizActivities.ChampionQuizEndless;
import com.example.quiztest2.championQuizActivities.ChampionQuizTimeAttack;
import com.example.quiztest2.championQuizActivities.ChampionQuizTraining;
import com.scp.leagueofquiz.R;

public class ChampionQuizModeSelect extends AppCompatActivity {
    Button trainingFirst, trainingSecond, trainingThird, timeFirst, timeSecond, timeThird;
    Button endlessButton, marathonButton;

    public static final String KEY_TRAIN = "EXTRA_TRAINING";
    private static final int TRAIN_ONE_CHAMP_COUNT = 20;
    private static final int TRAIN_TWO_CHAMP_COUNT = 50;
    private static final int TRAIN_THREE_CHAMP_COUNT = 100;
    private static final int ENDLESS_CHAMP_COUNT = 2147483645;

    public static final String KEY_TIME = "EXTRA_TIME";
    private static final long TIME_ONE_MILLIS = 30000;
    private static final long TIME_TWO_MILLIS = 60000;
    private static final long TIME_THREE_MILLIS = 120000;

    public static final String KEY_GAME_MODE = "EXTRA_GAME_MODE";
    public static final String MODE_TRAINING = "TRAINING";
    public static final String MODE_TIME_ATTACK = "TIME_ATTACK";
    public static final String MODE_ENDLESS = "ENDLESS";
    public static final String MODE_MARATHON = "MARATHON";

    public static final String KEY_TIMEATTACK = "EXTRA_TIME_ATTACK_BOOL";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion_quiz_game_mode);

        trainingFirst = findViewById(R.id.trainFirstButton);
        trainingSecond = findViewById(R.id.trainSecondButton);
        trainingThird = findViewById(R.id.trainThirdButton);
        timeFirst = findViewById(R.id.timeFirstButton);
        timeSecond = findViewById(R.id.timeSecondButton);
        timeThird = findViewById(R.id.timeThirdButton);
        endlessButton = findViewById(R.id.endlessButton);
        marathonButton = findViewById(R.id.marathonButton);

        trainingFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this, ChampionQuizTraining.class);
                intent.putExtra(KEY_TRAIN,TRAIN_ONE_CHAMP_COUNT);
                intent.putExtra(KEY_GAME_MODE,MODE_TRAINING);
                startActivity(intent);
                finish();
            }
        });

        trainingSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this,ChampionQuizTraining.class);
                intent.putExtra(KEY_TRAIN,TRAIN_TWO_CHAMP_COUNT);
                intent.putExtra(KEY_GAME_MODE,MODE_TRAINING);
                startActivity(intent);
                finish();
            }
        });

        trainingThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this,ChampionQuizTraining.class);
                intent.putExtra(KEY_TRAIN,TRAIN_THREE_CHAMP_COUNT);
                intent.putExtra(KEY_GAME_MODE,MODE_TRAINING);
                startActivity(intent);
                finish();
            }
        });

        timeFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this, ChampionQuizTimeAttack.class);
                intent.putExtra(KEY_TIME,TIME_ONE_MILLIS);
                intent.putExtra(KEY_GAME_MODE,MODE_TIME_ATTACK);
                startActivity(intent);
                finish();
            }
        });

        timeSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this,ChampionQuizTimeAttack.class);
                intent.putExtra(KEY_TIME,TIME_TWO_MILLIS);
                intent.putExtra(KEY_GAME_MODE,MODE_TIME_ATTACK);
                startActivity(intent);
                finish();
            }
        });

        timeThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this,ChampionQuizTimeAttack.class);
                intent.putExtra(KEY_TIME,TIME_THREE_MILLIS);
                intent.putExtra(KEY_GAME_MODE,MODE_TIME_ATTACK);
                startActivity(intent);
                finish();
            }
        });

        endlessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this, ChampionQuizEndless.class);
                intent.putExtra(KEY_TRAIN,ENDLESS_CHAMP_COUNT);
                intent.putExtra(KEY_GAME_MODE,MODE_ENDLESS);
                startActivity(intent);
                finish();
                //
            }
        });

        marathonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChampionQuizModeSelect.this, ChampionQuizActivity.class);
                intent.putExtra(KEY_GAME_MODE, MODE_MARATHON);
                startActivity(intent);
                finish();
            }
        });


    }
}