package com.scp.leagueofquiz.repository;

import com.google.common.util.concurrent.ListenableFuture;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChampionRepository {
  private final ChampionDao championDao;

  @Inject
  public ChampionRepository(ChampionDao championDao) {
    this.championDao = championDao;
  }

  public ListenableFuture<List<Champion>> getRandomChampions(
      Set<Champion> championsAnswered, int howMany) {
    List<String> namesAnswered =
        championsAnswered.stream().map(champion -> champion.name).collect(Collectors.toList());
    return championDao.findRandomChampsExcept(namesAnswered, howMany);
  }
}
