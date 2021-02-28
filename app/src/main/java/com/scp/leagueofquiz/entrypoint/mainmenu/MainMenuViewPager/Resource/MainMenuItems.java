package com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.Resource;

import com.scp.leagueofquiz.R;
import com.scp.leagueofquiz.entrypoint.mainmenu.MainMenuViewPager.MainMenuItem;
import com.scp.leagueofquiz.entrypoint.shared.QuizType;
import java.util.ArrayList;
import java.util.Arrays;

public class MainMenuItems {
  public static ArrayList<MainMenuItem> getMainMenuItems() {
    return MAIN_MENU_ITEMS;
  }

  public static final MainMenuItem CHAMPION_QUIZ =
      new MainMenuItem("Champion Quiz", R.drawable.main_menu_champ, QuizType.CHAMPION);
  public static final MainMenuItem ABILITY_QUIZ =
      new MainMenuItem("Ability Quiz", R.drawable.ahri, QuizType.ABILITY);
  public static final MainMenuItem ITEM_QUIZ =
      new MainMenuItem("Item Quiz", R.drawable.azir, QuizType.ITEM);

  public static ArrayList<MainMenuItem> MAIN_MENU_ITEMS =
      new ArrayList<>(Arrays.asList(CHAMPION_QUIZ, ABILITY_QUIZ, ITEM_QUIZ));
}
