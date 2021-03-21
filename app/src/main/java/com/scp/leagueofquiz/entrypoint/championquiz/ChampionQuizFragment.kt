package com.scp.leagueofquiz.entrypoint.championquiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.databinding.ChampionQuizFragmentBinding;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import dagger.hilt.android.AndroidEntryPoint;
import java.time.Duration;
import java.util.List;
import java.util.Locale;

@AndroidEntryPoint
public class ChampionQuizFragment extends Fragment {

  private ChampionQuizFragmentBinding binding;
  private ChampionQuizViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(ChampionQuizViewModel.class);

    viewModel.getTimer().setValue(Duration.ZERO);
    Bundle argsBundle = getArguments();
    if (argsBundle != null) {
      ChampionQuizFragmentArgs args = ChampionQuizFragmentArgs.fromBundle(argsBundle);
      viewModel.setQuizMode(args.getMode());
      viewModel.setChampionCount(args.getChampCount());
      viewModel.getTimer().setValue(Duration.ofMillis(args.getTime()));
      if (viewModel.getTimer().getValue().isNegative()) {
        viewModel.getTimer().setValue(Duration.ZERO);
      }
    }
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
    setTimeAttackLineVisibility(viewModel.getQuizMode());
    binding.startQuizButton.setOnClickListener(this::startQuiz);

    // Setup observers
    viewModel.getChampionGrid().observe(getViewLifecycleOwner(), this::setChampionsGrid);
    viewModel.getScore().observe(getViewLifecycleOwner(), this::setScore);
    viewModel.getFailedAttempts().observe(getViewLifecycleOwner(), this::failedAttempt);
    viewModel.getStartTime().observe(getViewLifecycleOwner(), t -> setupStartButton());
    viewModel.getTimer().observe(getViewLifecycleOwner(), this::setTimer);
    viewModel.getRightChampion().observe(getViewLifecycleOwner(), this::setRightChampionName);
    viewModel.getQuizFinished().observe(getViewLifecycleOwner(), this::checkQuizFinished);
    viewModel.getButtonText().observe(getViewLifecycleOwner(), this::buttonText);
  }

  private void buttonText(String s) {
    binding.startQuizButton.setText(s);
  }

  @SuppressLint("SetTextI18n")
  private void failedAttempt(Integer integer) {
    if (integer != null && integer > 0) {
      Toast.makeText(requireContext(), "WRONG!", Toast.LENGTH_SHORT).show();
      if (viewModel.getQuizMode() == QuizMode.TIME) {
        binding.wrongsViewTimeAttack.setText(integer.toString());
      } else {
        binding.wrongViewNonTime.setText(integer.toString());
      }
    }
  }

  private void checkQuizFinished(Boolean isQuizFinished) {
    if (Boolean.TRUE.equals(isQuizFinished)) {
      navigateToResult();
    }
  }

  private void navigateToResult() {
    NavHostFragment.findNavController(this)
        .navigate(
            ChampionQuizFragmentDirections.goToResult(
                viewModel.getScore().getValue(),
                viewModel.getTimer().getValue().toMillis(),
                viewModel.getQuizMode(),
                viewModel.getFailedAttempts().getValue()));
  }

  private void setRightChampionName(Champion champion) {
    binding.championText.setText(champion.getName());
  }

  private void pickAnswer(View view) {
    int champIndex = Integer.parseInt(view.getTag().toString());
    viewModel.pickAnswer(champIndex);
  }

  private void setTimer(Duration timer) {
    String timeString =
        String.format(Locale.getDefault(), "%d:%02d", timer.toMinutes(), timer.getSeconds() % 60);

    if (viewModel.getQuizMode() == QuizMode.TIME) {
      if (timer.isZero()) {
        navigateToResult();
      }
      binding.countdownView.setText(timeString);
    } else {
      binding.timer.setText(timeString);
    }
  }

  private void setupStartButton() {
    binding.startQuizButton.setClickable(!viewModel.isQuizRunning());
    if (viewModel.isQuizRunning()) {
      if (viewModel.getQuizMode() == QuizMode.ENDLESS) {
        binding.startQuizButton.setText(R.string.buttonStopText);
        binding.startQuizButton.setClickable(true);
      } else {
        binding.startQuizButton.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.grey));
      }
      binding.btnAns1.setOnClickListener(this::pickAnswer);
      binding.btnAns2.setOnClickListener(this::pickAnswer);
      binding.btnAns3.setOnClickListener(this::pickAnswer);
      binding.btnAns4.setOnClickListener(this::pickAnswer);
    } else {
      binding.startQuizButton.setBackgroundColor(
          ContextCompat.getColor(requireContext(), R.color.purple_500));
      binding.btnAns1.setOnClickListener(null);
      binding.btnAns2.setOnClickListener(null);
      binding.btnAns3.setOnClickListener(null);
      binding.btnAns4.setOnClickListener(null);
    }
  }

  @SuppressLint("SetTextI18n")
  private void setScore(Integer score) {
    if (viewModel.getQuizMode() == QuizMode.TIME) {
      binding.scoreViewTimeAttack.setText(score.toString());
    } else {
      binding.scoreViewNonTime.setText(score.toString());
    }
  }

  private void startQuiz(View view) {
    if (viewModel.isQuizRunning()) {
      if (viewModel.getQuizMode() == QuizMode.ENDLESS) {
        navigateToResult();
      }
    } else {
      viewModel.startQuiz();
    }
  }

  private void setChampionsGrid(List<Champion> champions) {
    binding.btnAns1.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(0).getIdentifier(), "drawable", requireActivity().getPackageName()));
    binding.btnAns2.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(1).getIdentifier(), "drawable", requireActivity().getPackageName()));
    binding.btnAns3.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(2).getIdentifier(), "drawable", requireActivity().getPackageName()));
    binding.btnAns4.setImageResource(
        getResources()
            .getIdentifier(
                champions.get(3).getIdentifier(), "drawable", requireActivity().getPackageName()));
  }

  private void setTimeAttackLineVisibility(QuizMode quizMode) {
    if (quizMode == QuizMode.TIME) {
      binding.timeAttackLayout.setVisibility(View.VISIBLE);
    } else {
      binding.timeAttackLayout.setVisibility(View.INVISIBLE);
    }
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
