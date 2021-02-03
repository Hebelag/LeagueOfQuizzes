package com.scp.leagueofquiz.repository;

import android.content.Context;
import com.example.quiztest2.championQuizActivities.ChampionQuizLogic;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChampionRepository {
  private final ChampionQuizLogic championQuizLogic;

  public ChampionRepository(ChampionQuizLogic championQuizLogic) {
    this.championQuizLogic = championQuizLogic;
  }

  public List<QuizChampion> getRandomChampions(
      Context context, Set<QuizChampion> championsAnswered) {
    String[] buttonChampionsKey = championQuizLogic.getChampionKeyArray(context, championsAnswered);
    String[] championArray = championQuizLogic.getChampionNameArray(context, buttonChampionsKey);
    String[] buttonChampionsImages =
        championQuizLogic.getChampionIDArray(context, buttonChampionsKey);

    List<QuizChampion> result = new ArrayList<>();
    for (int i = 0; i < buttonChampionsKey.length; i++) {
      QuizChampion champion =
          new QuizChampion(buttonChampionsImages[i], buttonChampionsKey[i], championArray[i]);
      result.add(champion);
    }

    return result;
  }
}
