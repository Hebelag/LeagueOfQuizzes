package com.scp.leagueofquiz.entrypoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quiztest2.AbilityQuizActivity;
import com.example.quiztest2.ChampionQuizModeSelect;
import com.example.quiztest2.ItemQuizActivity;
import com.example.quiztest2.MainMenuQuiz;
import com.example.quiztest2.TestActitity;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuFragment;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CHAMPION_QUIZ = 1;
    private static final int REQUEST_CODE_ITEM_QUIZ = 2;
    private static final int REQUEST_CODE_ABILITY_QUIZ = 3;
    private static final int REQUEST_CODE_TEST = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainMenuFragment fragment = new MainMenuFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, fragment)
                    .commit();
        }
    }

    public void startChampQuiz(){
        Intent intent = new Intent(this, ChampionQuizModeSelect.class);
        startActivityForResult(intent, REQUEST_CODE_CHAMPION_QUIZ);
    }
    public void startItemQuiz(){
        Intent intent = new Intent(this,ItemQuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ITEM_QUIZ);
    }
    public void startAbilityQuiz(){
        Intent intent = new Intent(this,AbilityQuizActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ABILITY_QUIZ);
    }
    public void startTest(){
        Intent intent = new Intent(this, TestActitity.class);
        startActivityForResult(intent, REQUEST_CODE_TEST);
    }
}