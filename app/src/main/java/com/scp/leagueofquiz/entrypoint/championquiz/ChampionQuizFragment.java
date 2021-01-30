package com.scp.leagueofquiz.entrypoint.championquiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.databinding.ChampionQuizFragmentBinding;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

public class ChampionQuizFragment extends Fragment {

  private ChampionQuizFragmentBinding binding;
  private ChampionQuizViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(ChampionQuizViewModel.class);

    Bundle argsBundle = getArguments();
    if (argsBundle != null) {
      ChampionQuizFragmentArgs args = ChampionQuizFragmentArgs.fromBundle(argsBundle);
      viewModel.setQuizMode(args.getMode());
      viewModel.getChampionCount().setValue(args.getChampCount());
      viewModel.getTimer().setValue(Duration.ofMillis(args.getTime()));
    }
    viewModel.getScore().setValue(0);
    viewModel.getTimer().setValue(Duration.ZERO);
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = ChampionQuizFragmentBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Setup UI
    setScoreLineVisibility(viewModel.getQuizMode());
    binding.startQuizButton.setOnClickListener(this::startQuiz);
    setTimer(Duration.ZERO);

    // Setup observers
    viewModel.getChampionGrid().observe(getViewLifecycleOwner(), this::setChampionsGrid);
    viewModel.getScore().observe(getViewLifecycleOwner(), this::setScore);
    viewModel.getStartTime().observe(getViewLifecycleOwner(), this::setupStartButton);
    viewModel.getTimer().observe(getViewLifecycleOwner(), this::setTimer);
    viewModel.getRightChampion().observe(getViewLifecycleOwner(), this::setRightChampionName);
  }

  private void setRightChampionName(QuizChampion quizChampion) {
    binding.championText.setText(quizChampion.getName());
  }

  private void pickAnswer(View view) {
    int champIndex = Integer.parseInt(view.getTag().toString());
    viewModel.pickAnswer(champIndex);
  }

  private void setTimer(Duration timer) {
    String timeString =
        String.format(Locale.getDefault(), "%d:%02d", timer.toMinutes(), timer.getSeconds() % 60);
    binding.timer.setText(timeString);
  }

  private void setupStartButton(Instant startTime) {
    binding.startQuizButton.setClickable(startTime == null);
    if (startTime == null) {
      // No quiz running
      binding.startQuizButton.setBackgroundColor(
          ContextCompat.getColor(getContext(), R.color.purple_500));

      binding.btnAns1.setOnClickListener(null);
      binding.btnAns2.setOnClickListener(null);
      binding.btnAns3.setOnClickListener(null);
      binding.btnAns4.setOnClickListener(null);
    } else {
      // Quiz is running
      binding.startQuizButton.setBackgroundColor(
          ContextCompat.getColor(getContext(), R.color.grey));

      binding.btnAns1.setOnClickListener(this::pickAnswer);
      binding.btnAns2.setOnClickListener(this::pickAnswer);
      binding.btnAns3.setOnClickListener(this::pickAnswer);
      binding.btnAns4.setOnClickListener(this::pickAnswer);
    }
  }

  @SuppressLint("SetTextI18n")
  private void setScore(Integer score) {
    binding.scoreViewNonTime.setText(score.toString());
  }

  private void startQuiz(View view) {
    viewModel.startQuiz();
  }

  private void setChampionsGrid(List<QuizChampion> champions) {
    binding.btnAns1.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(0).getId(), "drawable", requireActivity().getPackageName()));
    binding.btnAns2.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(1).getId(), "drawable", requireActivity().getPackageName()));
    binding.btnAns3.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(2).getId(), "drawable", requireActivity().getPackageName()));
    binding.btnAns4.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(3).getId(), "drawable", requireActivity().getPackageName()));
  }

  private void setScoreLineVisibility(QuizMode quizMode) {
    if (quizMode == QuizMode.TIME) {
      binding.scoreLineTimeAttack.setVisibility(View.VISIBLE);
      binding.scoreLineNonTime.setVisibility(View.INVISIBLE);
    } else {
      binding.scoreLineTimeAttack.setVisibility(View.INVISIBLE);
      binding.scoreLineNonTime.setVisibility(View.VISIBLE);
    }
  }
}
