package com.scp.leagueofquiz.api.database;

import android.content.Context;
import androidx.room.Room;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
  private static final String DATABASE_NAME = "lol-database";

  @Provides
  public LolDatabase provideLolDatabase(@ApplicationContext Context applicationContext) {
    return Room.databaseBuilder(applicationContext, LolDatabase.class, DATABASE_NAME).build();
  }

  @Provides
  public ChampionDao provideChampionDao(LolDatabase db) {
    return db.championDao();
  }
}
