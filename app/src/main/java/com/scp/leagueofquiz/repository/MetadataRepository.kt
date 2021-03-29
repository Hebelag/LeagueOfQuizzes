package com.scp.leagueofquiz.repository

import android.content.Context
import com.google.gson.Gson
import com.scp.leagueofquiz.api.database.ChampionJSONRoot
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import com.scp.leagueofquiz.api.database.metadata.MetadataDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MetadataRepository @Inject constructor(
        @param:ApplicationContext private val applicationContext: Context,
        private val metadataDao: MetadataDao,
        private val championDao: ChampionDao,
        private val gson: Gson) {

    fun startDatabaseUpdate() {
        Timber.i("Checking database update availability")

        // Run this in the default thread pool that runs coroutines
        CoroutineScope(Dispatchers.Default).launch {
            // findAll() is a coroutine so the thread inside launch will pause until the data is
            // retrieved
            val metadata = metadataDao.findAll()

            // Data is retrieved so we continue by checking if the database is new, and if yes,
            // we run the update; handleDatabaseUpdate() is also a coroutine
            if (metadata.isEmpty()) {
                handleDatabaseUpdate()
            }
        }
    }

    private suspend fun handleDatabaseUpdate() {
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

    private fun downloadChampionJSON(): String {



        lateinit var stream: InputStream
        lateinit var urlConnection: HttpURLConnection
        var inputAsString = ""
        try{
            val url = URL("http://ddragon.leagueoflegends.com/cdn/11.6.1/data/en_US/championFull.json")
            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()
            stream = urlConnection.inputStream
            inputAsString = stream.bufferedReader().readText()

        }catch(e: MalformedURLException){
            e.printStackTrace()
        }catch(e: IOException){
            e.printStackTrace()
        }finally{
            urlConnection.disconnect()
        }
        return inputAsString
    }

    /* This is a retrofit stub to maybe implement in the future for more flexible data fetching
    fun downloadNewVersion(): String{
        val retrofit = Retrofit
                .Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://ddragon.leagueoflegends.com/")
                .build()

        val service = retrofit.create(DownloadFiles::class.java)
        val championFullCall = service.getChampionFull()

        var championFull = ""
        championFullCall.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    championFull = response.body().toString()
                    println("HELLO")
                    println(response.body())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return championFull

    }*/

    /**
     * This method will one day check online for availability of an update of the data, compare it
     * with the data currently owned, and perform an update if needed. For now, it simply checks if
     * the database is empty, and if yes, loads the embedded champion json.
     */

    private fun loadJsonRoot(): ChampionJSONRoot {


        // Anstatt applicationContext.assets.open(EMBEDDED_JSON_NAME).use
        // Soll die Datei aus dem Internet geladen werden und sofort als String hier reinkopiert werden
        try {
            return gson.fromJson(downloadChampionJSON(), ChampionJSONRoot::class.java)

        } catch (e: IOException) {
            Timber.e(e, "Error while loading embedded json")
            throw RuntimeException(e)
        }

    }
}