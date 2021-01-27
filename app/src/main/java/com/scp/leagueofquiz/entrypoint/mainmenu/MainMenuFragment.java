package com.scp.leagueofquiz.entrypoint.mainmenu;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

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
import com.scp.leagueofquiz.databinding.MainMenuFragmentBinding;
import com.scp.leagueofquiz.entrypoint.MainActivity;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;

public class MainMenuFragment extends Fragment {
    private MainMenuFragmentBinding binding;
    private MainMenuViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainMenuFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonChampQuiz.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.CHAMPION)));
        binding.buttonItemQuiz.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ITEM)));
        binding.buttonAbilityQuiz.setOnClickListener(v -> NavHostFragment.findNavController(this).navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ABILITY)));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
        // TODO: Use the ViewModel
    }
}