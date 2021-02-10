package com.scp.leagueofquiz.entrypoint.quizresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.scp.leagueofquiz.databinding.QuizResultFragmentBinding;
import com.scp.leagueofquiz.entrypoint.shared.QuizMode;
import java.time.Duration;
import java.util.Locale;

public class QuizResultFragment extends Fragment {

  // public static QuizResultFragment newInstance() {
  //  return new QuizResultFragment();
  // }

  private QuizResultFragmentBinding binding;
  private QuizResultViewModel viewModel;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(QuizResultViewModel.class);

    Bundle argsBundle = getArguments();
    if (argsBundle != null) {
      QuizResultFragmentArgs args = QuizResultFragmentArgs.fromBundle(argsBundle);
      viewModel.setQuizMode(args.getQuizmode());
      viewModel.getScore().setValue(args.getScore());
      viewModel.getTimer().setValue(Duration.ofMillis(args.getTimer()));
      viewModel.getFailedAttempts().setValue(args.getFailedAttempts());
    }
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = QuizResultFragmentBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    binding.exitButton.setOnClickListener(
        v ->
            NavHostFragment.findNavController(this)
                .navigate(
                    QuizResultFragmentDirections.actionQuizResultFragmentToMainMenuFragment()));

    setScoreLineVisibility(viewModel.getQuizMode());

    viewModel.getScore().observe(getViewLifecycleOwner(), this::setScore);
    viewModel.getFailedAttempts().observe(getViewLifecycleOwner(), this::failedAttempt);
    viewModel.getTimer().observe(getViewLifecycleOwner(), this::setTimer);
  }

  private void setTimer(Duration duration) {
    String timeString = "";
    if (duration.toHours() > 0) {
      timeString =
          String.format(
              Locale.getDefault(),
              "%d:%02d:%02d",
              duration.toHours(),
              duration.toMinutes(),
              duration.getSeconds() % 60);
    } else {
      if (duration.isZero()) {}
      timeString =
          String.format(
              Locale.getDefault(), "%d:%02d", duration.toMinutes(), duration.getSeconds() % 60);
    }
    binding.resultTime.setText(timeString);
  }

  private void failedAttempt(Integer failedAttempts) {
    binding.resultWrong.setText("Wrong champions answered: " + failedAttempts.toString());
  }

  private void setScore(Integer score) {
    if (viewModel.getQuizMode() == QuizMode.TIME) {
      binding.resultScoreTimeAttack.setText("Final Score: " + score.toString());
    } else {
      binding.resultScoreNonTime.setText("Champions: " + score.toString());
    }
  }

  private void setScoreLineVisibility(QuizMode quizMode) {
    if (quizMode == QuizMode.TIME) {
      binding.resultScoreTimeAttack.setVisibility(View.VISIBLE);
      binding.resultScoreNonTime.setVisibility(View.INVISIBLE);
    } else {
      binding.resultScoreTimeAttack.setVisibility(View.INVISIBLE);
      binding.resultScoreNonTime.setVisibility(View.VISIBLE);
    }
  }
}
