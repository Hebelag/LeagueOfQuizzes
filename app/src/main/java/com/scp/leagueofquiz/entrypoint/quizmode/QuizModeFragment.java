package com.scp.leagueofquiz.entrypoint.quizmode;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quiztest2.ChampionQuizModeSelect;
import com.example.quiztest2.championQuizActivities.ChampionQuizActivity;
import com.example.quiztest2.championQuizActivities.ChampionQuizEndless;
import com.example.quiztest2.championQuizActivities.ChampionQuizTimeAttack;
import com.example.quiztest2.championQuizActivities.ChampionQuizTraining;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuFragmentDirections;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;

public class QuizModeFragment extends Fragment {

    private QuizModeViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.quiz_mode_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(QuizModeViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.trainFirstButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING);
            action.setChampCount(20);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.trainSecondButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING);
            action.setChampCount(50);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.trainThirdButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING);
            action.setChampCount(100);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.timeFirstButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME);
            action.setTime(30000);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.timeSecondButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME);
            action.setTime(60000);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.timeThirdButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TIME);
            action.setTime(120000);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.endlessButton).setOnClickListener(v -> {
            QuizModeFragmentDirections.StartChampQuiz action = QuizModeFragmentDirections.startChampQuiz(QuizMode.TRAINING);
            action.setChampCount(Integer.MAX_VALUE);
            NavHostFragment.findNavController(this).navigate(action);
        });
        view.findViewById(R.id.marathonButton).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(QuizModeFragmentDirections.startChampQuiz(QuizMode.MARATHON));
        });
    }
}