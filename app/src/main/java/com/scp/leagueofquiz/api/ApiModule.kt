package com.scp.leagueofquiz.api;

import android.content.Context;
import androidx.room.Room;
import com.google.gson.Gson;
import com.scp.leagueofquiz.api.database.LolDatabase;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;
import com.scp.leagueofquiz.api.database.metadata.MetadataDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {
  private static final String DATABASE_NAME = "lol-database";

  @Singleton
  @Provides
  public LolDatabase provideLolDatabase(@ApplicationContext Context applicationContext) {
    return Room.databaseBuilder(applicationContext, LolDatabase.class, DATABASE_NAME).build();
  }

  @Singleton
  @Provides
  public ChampionDao provideChampionDao(LolDatabase db) {
    return db.championDao();
  }

  @Singleton
  @Provides
  public MetadataDao provideMetadataDao(LolDatabase db) {
    return db.metadataDao();
  }

  @Singleton
  @Provides
  Gson provideGson() {
    return new Gson();
  }
}
