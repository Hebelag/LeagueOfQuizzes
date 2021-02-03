package com.scp.leagueofquiz.repository;

import android.content.Context;
import com.example.quiztest2.championQuizActivities.ChampionQuizLogic;
import com.scp.leagueofquiz.entrypoint.shared.QuizChampion;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;

public class ChampionRepository {
  private final ChampionQuizLogic championQuizLogic;
  private final Context applicationContext;

  @Inject
  public ChampionRepository(
      ChampionQuizLogic championQuizLogic, @ApplicationContext Context applicationContext) {
    this.championQuizLogic = championQuizLogic;
    this.applicationContext = applicationContext;
  }

  public List<QuizChampion> getRandomChampions(Set<QuizChampion> championsAnswered) {
    String[] buttonChampionsKey =
        championQuizLogic.getChampionKeyArray(applicationContext, championsAnswered);
    String[] championArray =
        championQuizLogic.getChampionNameArray(applicationContext, buttonChampionsKey);
    String[] buttonChampionsImages =
        championQuizLogic.getChampionIDArray(applicationContext, buttonChampionsKey);

    List<QuizChampion> result = new ArrayList<>();
    for (int i = 0; i < buttonChampionsKey.length; i++) {
      QuizChampion champion =
          new QuizChampion(buttonChampionsImages[i], buttonChampionsKey[i], championArray[i]);
      result.add(champion);
    }

    return result;
  }
}
