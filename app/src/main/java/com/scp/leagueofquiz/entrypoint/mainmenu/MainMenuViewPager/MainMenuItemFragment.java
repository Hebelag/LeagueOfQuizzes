package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuFragmentDirections;

public class MainMenuItemFragment extends Fragment {

  // Widgets
  private ImageView mImageView;
  private TextView mTitle;

  // Variables
  private MainMenuItem mMainMenuItem;

  public static MainMenuItemFragment getInstance(MainMenuItem mainMenuItem) {
    MainMenuItemFragment fragment = new MainMenuItemFragment();

    if (mainMenuItem != null) {
      Bundle bundle = new Bundle();
      bundle.putParcelable("mainMenuItem", mainMenuItem);
      fragment.setArguments(bundle);
    }
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mMainMenuItem = getArguments().getParcelable("mainMenuItem");
    }
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main_menu_viewpager_item, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    mImageView = view.findViewById(R.id.image);
    mTitle = view.findViewById(R.id.title);
    init();
  }

  private void init() {
    if (mMainMenuItem != null) {
      RequestOptions options = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
      Glide.with(getActivity())
          .setDefaultRequestOptions(options)
          .load(mMainMenuItem.getImage())
          .into(mImageView);
      mTitle.setText(mMainMenuItem.getTitle());
      mImageView.setOnClickListener(
          v ->
              NavHostFragment.findNavController(
                      getParentFragmentManager().getPrimaryNavigationFragment())
                  .navigate(
                      MainMenuFragmentDirections.actionQuizMode(mMainMenuItem.getQuizType())));
    }
  }
}
