package com.scp.leagueofquiz.api.database.champion;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

@Dao
public interface ChampionDao {
  @Insert
  void insertAll(List<Champion> champions);

  @Query("SELECT * FROM champion")
  ListenableFuture<List<Champion>> findAll();

  @Query("SELECT * FROM champion WHERE name NOT IN (:names) ORDER BY random() LIMIT :quantity")
  ListenableFuture<List<Champion>> findRandomChampsExcept(List<String> names, int quantity);
}
