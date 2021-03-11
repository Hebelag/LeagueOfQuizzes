package com.scp.leagueofquiz.api.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;
import com.scp.leagueofquiz.api.database.champion.Converters;
import com.scp.leagueofquiz.api.database.metadata.Metadata;
import com.scp.leagueofquiz.api.database.metadata.MetadataDao;

@Database(
    entities = {Champion.class, Metadata.class},
    version = 1)
@TypeConverters({Converters.class})
public abstract class LolDatabase extends RoomDatabase {
  public abstract ChampionDao championDao();

  public abstract MetadataDao metadataDao();
}
