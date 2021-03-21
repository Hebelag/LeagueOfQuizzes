package com.scp.leagueofquiz

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initialiseApp()
    }

    private fun initialiseApp() {
        // Setup logger
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}