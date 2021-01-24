package com.example.quiztest2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.scp.leagueofquiz.R;

public class MainMenuQuiz extends AppCompatActivity {
    private static final int REQUEST_CODE_CHAMPION_QUIZ = 1;
    private static final int REQUEST_CODE_ITEM_QUIZ = 2;
    private static final int REQUEST_CODE_ABILITY_QUIZ = 3;
    private static final int REQUEST_CODE_TEST = 99;

    Button buttonStartChampionQuiz, buttonStartItemQuiz, buttonStartAbilityQuiz, buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu_quiz);

        bindButtons();
    }

    private void bindButtons() {
        buttonStartChampionQuiz = findViewById(R.id.buttonChampQuiz);
        buttonStartItemQuiz = findViewById(R.id.buttonItemQuiz);
        buttonStartAbilityQuiz = findViewById(R.id.buttonAbilityQuiz);
        buttonTest = findViewById(R.id.buttonTEST);

        buttonStartChampionQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChampQuiz();
            }
        });
        buttonStartItemQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startItemQuiz();
            }
        });
        buttonStartAbilityQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAbilityQuiz();
            }
        });
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }

    private void startChampQuiz(){
        Intent intent = new Intent(MainMenuQuiz.this, ChampionQuizModeSelect.class);
        startActivityForResult(intent, REQUEST_CODE_CHAMPION_QUIZ);
    }
    private void startItemQuiz(){
        Intent intent = new Intent(MainMenuQuiz.this,ItemQuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ITEM_QUIZ);
    }
    private void startAbilityQuiz(){
        Intent intent = new Intent(MainMenuQuiz.this,AbilityQuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ABILITY_QUIZ);
    }
    private void startTest(){
        Intent intent = new Intent(MainMenuQuiz.this, TestActitity.class);
        startActivityForResult(intent, REQUEST_CODE_TEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHAMPION_QUIZ){
            if (resultCode == RESULT_OK){

            }
        }
    }
}