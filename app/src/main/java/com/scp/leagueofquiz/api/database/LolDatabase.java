package com.scp.leagueofquiz.api.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;

@Database(
    entities = {Champion.class},
    version = 1)
public abstract class LolDatabase extends RoomDatabase {
  public abstract ChampionDao championDao();
}
