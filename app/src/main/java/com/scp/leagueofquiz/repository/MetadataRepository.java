package com.scp.leagueofquiz.repository;

import android.content.Context;
import com.example.quiztest2.json.ChampionJSON;
import com.example.quiztest2.json.ChampionJSONRoot;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.scp.leagueofquiz.api.database.champion.Champion;
import com.scp.leagueofquiz.api.database.champion.ChampionDao;
import com.scp.leagueofquiz.api.database.metadata.Metadata;
import com.scp.leagueofquiz.api.database.metadata.MetadataDao;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.inject.Singleton;
import timber.log.Timber;

@Singleton
public class MetadataRepository {
  private static final String EMBEDDED_JSON_NAME = "championFull.json";
  private static final Duration UPDATE_FREQUENCE = Duration.ofDays(1);

  private final Context applicationContext;
  private final MetadataDao metadataDao;
  private final ChampionDao championDao;
  private final Gson gson;
  private final Executor executor;

  @Inject
  public MetadataRepository(
      @ApplicationContext Context applicationContext,
      MetadataDao metadataDao,
      ChampionDao championDao,
      Gson gson,
      Executor executor) {
    this.applicationContext = applicationContext;
    this.metadataDao = metadataDao;
    this.championDao = championDao;
    this.gson = gson;
    this.executor = executor;
  }

  public void startDatabaseUpdate() {
    Timber.i("Checking database update availability");
    ListenableFuture<List<Metadata>> metadataList = metadataDao.findAll();
    metadataList.addListener(() -> handleDatabaseUpdate(metadataList), executor);
  }

  /**
   * This method will one day check online for availability of an update of the data, compare it
   * with the data currently owned, and perform an update if needed. For now, it simply checks if
   * the database is empty, and if yes, loads the embedded champion json.
   */
  public void handleDatabaseUpdate(ListenableFuture<List<Metadata>> metadataList) {
    try {
      if (metadataList.get().isEmpty()) {
        Timber.i("Database outdated, populating...");
        ChampionJSONRoot jsonRoot = loadJsonRoot();
        List<Champion> champsToSave = new ArrayList<>();

        for (ChampionJSON championJSON : jsonRoot.getData().values()) {
          champsToSave.add(new Champion(championJSON.getId().toLowerCase(), championJSON.getName()));
        }

        championDao.insertAll(champsToSave);
        Timber.i("Database updated.");
      }
    } catch (ExecutionException | InterruptedException e) {
      Timber.e(e, "Error while updating the database");
    }
  }

  private ChampionJSONRoot loadJsonRoot() {
    try (InputStream is = applicationContext.getAssets().open(EMBEDDED_JSON_NAME)) {
      int size = is.available();
      byte[] buffer = new byte[size];
      is.read(buffer);
      is.close();

      String loadedJson = new String(buffer);
      return gson.fromJson(loadedJson, ChampionJSONRoot.class);
    } catch (IOException e) {
      Timber.e(e, "Error while loading embedded json");
      throw new RuntimeException(e);
    }
  }
}
