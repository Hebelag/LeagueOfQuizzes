package com.scp.leagueofquiz.api.database.champion;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ChampionDao {
  @Insert
  void insertAll(Champion... champions);

  @Query("SELECT * FROM champion")
  List<Champion> findAll();

  @Query("SELECT * FROM champion WHERE name NOT IN (:names) ORDER BY random() LIMIT :quantity")
  List<Champion> findRandomChampsExcept(List<String> names, int quantity);
}
