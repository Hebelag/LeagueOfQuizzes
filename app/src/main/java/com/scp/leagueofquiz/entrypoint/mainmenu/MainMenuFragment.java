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
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.scp.leagueofquiz.databinding.MainMenuFragmentBinding;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.MainMenuItem;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.MainMenuItemFragment;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.MainMenuPagerAdapter;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.Resource.MainMenuItems;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;
import dagger.hilt.android.AndroidEntryPoint;
import java.util.ArrayList;

@AndroidEntryPoint
public class MainMenuFragment extends Fragment {
  private MainMenuFragmentBinding binding;
  private MainMenuViewModel viewModel;

  // widgets
  private ViewPager mMainMenuViewPager;
  private TabLayout mTabLayout;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(MainMenuViewModel.class);
    // This is just a temporary place to where to initialise the database. We'll find a proper way
    // to manage this later
    viewModel.updateDatabase();
  }

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

    init();

    /*binding.buttonChampQuiz.setOnClickListener(
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
                .navigate(MainMenuFragmentDirections.actionQuizMode(QuizType.ABILITY)));*/
    binding.preferences.setOnClickListener(
        v -> NavHostFragment.findNavController(this)
    .navigate(MainMenuFragmentDirections.actionSettings()));
  }

  private void init() {
    ArrayList<Fragment> fragments = new ArrayList<>();
    ArrayList<MainMenuItem> mainMenuItems = MainMenuItems.getMainMenuItems();
    for (MainMenuItem mainMenuItem : mainMenuItems) {
      MainMenuItemFragment fragment = MainMenuItemFragment.getInstance(mainMenuItem);
      fragments.add(fragment);
    }
    MainMenuPagerAdapter pagerAdapter = new MainMenuPagerAdapter(getParentFragment(), fragments);
    binding.viewPager.setAdapter(pagerAdapter);
    new TabLayoutMediator(
            binding.tabLayout, binding.viewPager, true, true, (tab, position) -> tab.setText(""))
        .attach();
  }
}
