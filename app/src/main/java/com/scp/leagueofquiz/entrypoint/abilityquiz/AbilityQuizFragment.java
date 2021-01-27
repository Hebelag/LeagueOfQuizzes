package com.scp.leagueofquiz.entrypoint.abilityquiz;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scp.leagueofquiz.R;

public class AbilityQuizFragment extends Fragment {

    private AbilityQuizViewModel mViewModel;

    public static AbilityQuizFragment newInstance() {
        return new AbilityQuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ability_quiz_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AbilityQuizViewModel.class);
        // TODO: Use the ViewModel
    }

}