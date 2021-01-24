package com.scp.leagueofquiz.entrypoint.mainmenu;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.quiztest2.AbilityQuizActivity;
import com.example.quiztest2.ChampionQuizModeSelect;
import com.example.quiztest2.ItemQuizActivity;
import com.example.quiztest2.MainMenuQuiz;
import com.example.quiztest2.TestActitity;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.entrypoint.MainActivity;

public class MainMenuFragment extends Fragment {
    private MainMenuViewModel mViewModel;
    Button buttonStartChampionQuiz, buttonStartItemQuiz, buttonStartAbilityQuiz, buttonTest;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        // TODO: Use the ViewModel

        bindButtons();
    }

    private void bindButtons() {
        Activity activity = requireActivity();
        buttonStartChampionQuiz = activity.findViewById(R.id.buttonChampQuiz);
        buttonStartItemQuiz = activity.findViewById(R.id.buttonItemQuiz);
        buttonStartAbilityQuiz = activity.findViewById(R.id.buttonAbilityQuiz);
        buttonTest = activity.findViewById(R.id.buttonTEST);

        buttonStartChampionQuiz.setOnClickListener(v -> ((MainActivity)requireActivity()).startChampQuiz());
        buttonStartItemQuiz.setOnClickListener(v -> ((MainActivity)requireActivity()).startItemQuiz());
        buttonStartAbilityQuiz.setOnClickListener(v -> ((MainActivity)requireActivity()).startAbilityQuiz());
        buttonTest.setOnClickListener(v -> ((MainActivity)requireActivity()).startTest());
    }
}