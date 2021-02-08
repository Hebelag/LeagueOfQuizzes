package com.scp.leagueofquiz.repository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

  @Provides
  public Executor provideExecutor() {
    // TODO investigate the best configuration for this executor
    return Executors.newSingleThreadExecutor();
  }
}
