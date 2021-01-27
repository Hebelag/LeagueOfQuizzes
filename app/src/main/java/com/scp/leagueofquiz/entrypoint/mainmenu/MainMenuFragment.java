package com.scp.leagueofquiz.entrypoint.mainmenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.scp.leagueofquiz.databinding.MainMenuFragmentBinding;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;

public class MainMenuFragment extends Fragment {
  private MainMenuFragmentBinding binding;
  private MainMenuViewModel mViewModel;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = MainMenuFragmentBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.buttonChampQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.CHAMPION)));
    binding.buttonItemQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ITEM)));
    binding.buttonAbilityQuiz.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ABILITY)));
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
    // TODO: Use the ViewModel
  }
}
