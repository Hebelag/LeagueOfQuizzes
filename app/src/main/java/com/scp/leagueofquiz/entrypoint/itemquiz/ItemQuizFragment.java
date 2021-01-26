package com.scp.leagueofquiz.entrypoint.itemquiz;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scp.leagueofquiz.R;

public class ItemQuizFragment extends Fragment {

    private ItemQuizViewModel mViewModel;

    public static ItemQuizFragment newInstance() {
        return new ItemQuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_quiz_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ItemQuizViewModel.class);
        // TODO: Use the ViewModel
    }

}