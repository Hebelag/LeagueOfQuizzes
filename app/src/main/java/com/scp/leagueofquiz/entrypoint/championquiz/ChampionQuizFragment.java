package com.scp.leagueofquiz.entrypoint.championquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.scp.leagueofquiz.R;

public class ChampionQuizFragment extends Fragment {

  private ChampionQuizViewModel mViewModel;

  public static ChampionQuizFragment newInstance() {
    return new ChampionQuizFragment();
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.champion_quiz_fragment, container, false);
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mViewModel = new ViewModelProvider(this).get(ChampionQuizViewModel.class);
    // TODO: Use the ViewModel
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ChampionQuizFragmentArgs args = ChampionQuizFragmentArgs.fromBundle(getArguments());
    Toast.makeText(
            requireContext(),
            "MODE = "
                + args.getMode()
                + ", COUNT = "
                + args.getChampCount()
                + ", TIME = "
                + args.getTime(),
            Toast.LENGTH_LONG)
        .show();
  }
}
