package com.example.quiztest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestActitity extends AppCompatActivity {
    Spinner testSpinner;
    TextView testChamp, testLore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_actitity);
        testChamp = findViewById(R.id.testChampView);
        testLore = findViewById(R.id.testLoreView);
        testSpinner = findViewById(R.id.testSpinner);
        setUpAllTextViews();


    }

    public void setUpDropDown(){
        DBHelper db = DBHelper.getInstance(this);
        System.out.println("HELLO");

        List<ChampionJSON> allChampions = db.getAllChampions();
        List<String> allChampionsNames = new ArrayList<String>();
        int x = allChampions.size();
        for (int i = 0;i<x;i++){
            ChampionJSON champ = allChampions.get(i);
            allChampionsNames.add(champ.getName());
        }
        ArrayAdapter<String> adapterChampName = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allChampionsNames);
        adapterChampName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testSpinner.setAdapter(adapterChampName);
    }

    public void setUpAllTextViews(){
        DBHelper db = DBHelper.getInstance(this);
        System.out.println("HELLO");

        List<ChampionJSON> allChampions = db.getAllChampions();
        String championName = "";
        String championLore = "";
        championName = allChampions.get(1).getName();
        championLore = allChampions.get(1).getLore();

        testLore.setText(championLore);
        testChamp.setText(championName);
    }
}
