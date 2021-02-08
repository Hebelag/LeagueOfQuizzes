package com.scp.leagueofquiz;

import android.app.Application;
import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;
import timber.log.Timber.DebugTree;

@HiltAndroidApp
public class App extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initialiseApp();
  }

  private void initialiseApp() {
    // Setup logger
    Timber.plant(new DebugTree());
  }
}
