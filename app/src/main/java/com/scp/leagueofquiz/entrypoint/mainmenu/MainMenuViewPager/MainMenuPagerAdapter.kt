package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;

/*
Plan for the main menu:
Since there are multiple "tabs" where the user can navigate, it should be expandable until infinity
FragmentStatePagerAdapter suits perfectly (Coding with Mitch)
Every Fragment needs: Image, listener, title (, progress?, rank?)

 */

public class MainMenuPagerAdapter extends FragmentStateAdapter {
  private final ArrayList<Fragment> mFragments;

  public MainMenuPagerAdapter(@NonNull Fragment fragment, ArrayList<Fragment> fragments) {
    super(fragment);
    mFragments = fragments;
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    return mFragments.get(position);
  }

  @Override
  public int getItemCount() {
    return mFragments.size();
  }
}
