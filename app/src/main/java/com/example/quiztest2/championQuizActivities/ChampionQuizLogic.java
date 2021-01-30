package com.example.quiztest2.championQuizActivities;

import android.content.Context;
import com.example.quiztest2.dbstuff.DBHelper;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChampionQuizLogic {
  private static final int CHAMPION_COUNT = 152;

  public List<QuizChampion> getRandomChampions(
      Context context, Set<QuizChampion> championsAnswered) {
    String[] buttonChampionsKey = getChampionKeyArray(context, championsAnswered);
    String[] championArray = getChampionNameArray(context, buttonChampionsKey);
    String[] buttonChampionsImages = getChampionIDArray(context, buttonChampionsKey);

    List<QuizChampion> result = new ArrayList<>();
    for (int i = 0; i < buttonChampionsKey.length; i++) {
      QuizChampion champion =
          new QuizChampion(buttonChampionsImages[i], buttonChampionsKey[i], championArray[i]);
      result.add(champion);
    }

    return result;
  }

  public String[] initializeEmptyChampions() {
    String[] buttonChampions = new String[4];
    buttonChampions[0] = "defaultchampion";
    buttonChampions[1] = "defaultchampion";
    buttonChampions[2] = "defaultchampion";
    buttonChampions[3] = "defaultchampion";
    return buttonChampions;
  }

  public String[] getChampionKeyArray(Context context, Set<QuizChampion> answeredChampions) {
    DBHelper db = DBHelper.getInstance(context);
    String[] championKeys = new String[4];
    int[] uniqueChampionArray = new int[4];
    int championIndex;
    for (int i = 0; i < 4; i++) {
      do {
        championIndex = (int) (Math.random() * CHAMPION_COUNT);
      } while (contains(uniqueChampionArray, championIndex) || championIndex >= CHAMPION_COUNT);
      championKeys[i] = db.getChampKeyForChampQuiz(championIndex);
      uniqueChampionArray[i] = championIndex;
    }
    return championKeys;
  }

  public String[] getChampionNameArray(Context context, String[] keyArray) {
    DBHelper db = DBHelper.getInstance(context);
    String[] championNames = new String[4];
    for (int i = 0; i < 4; i++) {
      championNames[i] = db.getChampNameFromKey(keyArray[i]);
    }
    return championNames;
  }

  public String[] getChampionIDArray(Context context, String[] keyArray) {
    DBHelper db = DBHelper.getInstance(context);
    String[] championIDs = new String[4];
    for (int i = 0; i < 4; i++) {
      championIDs[i] = db.getChampIDFromKey(keyArray[i]);
    }
    return championIDs;
  }

  private boolean contains(int[] uniqueChampionArray, int championID) {
    for (int i : uniqueChampionArray) {
      if (championID == i) {
        return true;
      }
    }
    return false;
  }

  public String selectRightChampion(String[] buttonChampions) {
    int rightChampionPosition = (int) (Math.random() * 4);
    return buttonChampions[rightChampionPosition];
  }

  public QuizChampion selectRightChampion(List<QuizChampion> buttonChampions) {
    int rightChampionPosition = (int) (Math.random() * 4);
    return buttonChampions.get(rightChampionPosition);
  }
}
