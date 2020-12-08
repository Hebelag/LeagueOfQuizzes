package com.example.quiztest2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class MainMenuQuiz extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CHAMPION_QUIZ = 1;
    private static final int REQUEST_CODE_ITEM_QUIZ = 2;
    private static final int REQUEST_CODE_ABILITY_QUIZ = 3;
    private static final int REQUEST_CODE_TEST = 99;

    Button buttonStartChampionQuiz, buttonStartItemQuiz, buttonStartAbilityQuiz, buttonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu_quiz);


        /*
        if(firstAppStart()){

            createDatabase();

        }
        */

        buttonStartChampionQuiz = findViewById(R.id.buttonChampQuiz);
        buttonStartItemQuiz = findViewById(R.id.buttonItemQuiz);
        buttonStartAbilityQuiz = findViewById(R.id.buttonAbilityQuiz);
        buttonTest = findViewById(R.id.buttonTEST);

        buttonStartChampionQuiz.setOnClickListener(this);
        buttonStartItemQuiz.setOnClickListener(this);
        buttonStartAbilityQuiz.setOnClickListener(this);
        buttonTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonChampQuiz:
                startChampQuiz();
                break;
            case R.id.buttonItemQuiz:
                startItemQuiz();
                break;
            case R.id.buttonAbilityQuiz:
                startAbilityQuiz();
                break;
            case R.id.buttonTEST:
                startTest();
        }
    }
    private void startChampQuiz(){
        Intent intent = new Intent(MainMenuQuiz.this, ChampionQuizGameMode.class);
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



    /* public boolean firstAppStart(){
        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart,MODE_PRIVATE);
        if (preferences.getBoolean(prefNameFirstStart,true)){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(prefNameFirstStart, false);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public void createDatabase(){

    } */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHAMPION_QUIZ){
            if (resultCode == RESULT_OK){

            }
        }
    }
}