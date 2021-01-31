package com.scp.leagueofquiz.entrypoint.quizresult;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.scp.leagueofquiz.R;

public class QuizResultFragment extends Fragment {

  public static QuizResultFragment newInstance() {
    return new QuizResultFragment();
  }

  private QuizResultViewModel mViewModel;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.quiz_result_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = new ViewModelProvider(this).get(QuizResultViewModel.class);
    // TODO: Use the ViewModel
  }
}
