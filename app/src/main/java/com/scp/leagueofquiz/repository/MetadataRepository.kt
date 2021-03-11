package com.scp.leagueofquiz.repository

import android.content.Context
import com.google.common.util.concurrent.ListenableFuture
import com.google.gson.Gson
import com.scp.leagueofquiz.api.database.ChampionJSONRoot
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import com.scp.leagueofquiz.api.database.metadata.Metadata
import com.scp.leagueofquiz.api.database.metadata.MetadataDao
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetadataRepository @Inject constructor(
        @param:ApplicationContext private val applicationContext: Context,
        private val metadataDao: MetadataDao,
        private val championDao: ChampionDao,
        private val gson: Gson,
        private val executor: Executor) {

    fun startDatabaseUpdate() {
        Timber.i("Checking database update availability")
        val metadataList = metadataDao.findAll()
        metadataList!!.addListener(
                Runnable {
                    try {
                        if (metadataList.get()!!.isEmpty()) {
                            Timber.i("Database outdated, populating...")
                            val jsonRoot = loadJsonRoot()
                            val champsToSave: MutableList<Champion?> = ArrayList()
                            for (champion in jsonRoot.data.values) {
                                champsToSave.add(
                                        Champion(
                                                identifier = champion.identifier.toLowerCase(Locale.ROOT),
                                                name = champion.name,
                                                allytips = champion.allytips,
                                                blurb = champion.blurb,
                                                enemytips = champion.enemytips,
                                                info = champion.info,
                                                key = champion.key,
                                                title = champion.title,
                                                skins = champion.skins,
                                                lore = champion.lore,
                                                tags = champion.tags,
                                                partype = champion.partype,
                                                stats = champion.stats,
                                                spells = champion.spells,
                                                passive = champion.passive
                                        ))
                            }
                            championDao.insertAll(champsToSave)
                            Timber.i("Database updated.")
                        }
                    } catch (e: ExecutionException) {
                        Timber.e(e, "Error while updating the database")
                    } catch (e: InterruptedException) {
                        Timber.e(e, "Error while updating the database")
                    }
                },
                executor)
    }

    /**
     * This method will one day check online for availability of an update of the data, compare it
     * with the data currently owned, and perform an update if needed. For now, it simply checks if
     * the database is empty, and if yes, loads the embedded champion json.
     */

    private fun loadJsonRoot(): ChampionJSONRoot {
        try {
            applicationContext.assets.open(EMBEDDED_JSON_NAME).use { `is` ->
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                val loadedJson = String(buffer)
                return gson.fromJson(loadedJson, ChampionJSONRoot::class.java)
            }
        } catch (e: IOException) {
            Timber.e(e, "Error while loading embedded json")
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val EMBEDDED_JSON_NAME = "championFull.json"
    }
}