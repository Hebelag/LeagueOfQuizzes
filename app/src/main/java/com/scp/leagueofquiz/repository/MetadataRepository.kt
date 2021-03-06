package com.scp.leagueofquiz.repository

import android.content.Context
import com.google.gson.Gson
import com.scp.leagueofquiz.api.database.champion.ChampionRoot
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import com.scp.leagueofquiz.api.database.item.Item
import com.scp.leagueofquiz.api.database.item.ItemDao
import com.scp.leagueofquiz.api.database.item.ItemRoot
import com.scp.leagueofquiz.api.database.metadata.MetadataDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        private val itemDao: ItemDao,
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

        handleChampionUpdate()
        handleItemUpdate()

        Timber.i("Database updated.")
    }

    private suspend fun handleItemUpdate() {
        Timber.i("Start Item Database Update")
        val json = loadItemJSON()
        val itemsToSave: MutableList<Item?> = ArrayList()
        for (item in json.data.entries){
            itemsToSave.add(
                    Item(
                            identifier = item.key,
                            name = item.value.name,
                            gold = item.value.gold,
                            group = item.value.group,
                            description = item.value.description,
                            colloq = item.value.colloq,
                            plaintext = item.value.plaintext,
                            consumed = item.value.consumed,
                            stacks = item.value.stacks,
                            depth = item.value.depth,
                            effect = item.value.effect,
                            consumeOnFull = item.value.consumeOnFull,
                            from = item.value.from,
                            into = item.value.into,
                            specialRecipe = item.value.specialRecipe,
                            inStore = item.value.inStore,
                            hideFromAll = item.value.hideFromAll,
                            requiredChampion = item.value.requiredChampion,
                            requiredAlly = item.value.requiredAlly,
                            stats = item.value.stats,
                            tags = item.value.tags,
                            maps = item.value.maps,
                            image = item.value.image
                    )
            )
        }
        itemDao.insertAll(itemsToSave)
        Timber.i("Item Database Updated")
    }

    private fun loadItemJSON(): ItemRoot {
        return gson.fromJson(downloadJSON(ITEM_OBJECT),ItemRoot::class.java)
    }

    private suspend fun handleChampionUpdate() {
        Timber.i("Start Champion Database Update")
        val jsonRoot = loadChampionJson()
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
                            image = champion.image,
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
        Timber.i("Champion Database updated.")
    }
    /**
     * This method will one day check online for availability of an update of the data, compare it
     * with the data currently owned, and perform an update if needed. For now, it simply checks if
     * the database is empty, and if yes, loads the embedded champion json.
     */

    private fun loadChampionJson(): ChampionRoot {


        // Instead of applicationContext.assets.open(EMBEDDED_JSON_NAME).use
        // The data will be downloaded from the internet and directly passed as string without
        // Taking phone storage
        return gson.fromJson(downloadJSON("CHAMPION"), ChampionRoot::class.java)

    }

    private fun downloadJSON(objectType: String): String {

        lateinit var stream: InputStream
        lateinit var urlConnection: HttpURLConnection
        var urlString = ""
        when (objectType){
            ITEM_OBJECT ->  urlString = "http://ddragon.leagueoflegends.com/cdn/11.6.1/data/en_US/item.json"
            CHAMPION_OBJECT -> urlString  ="http://ddragon.leagueoflegends.com/cdn/11.6.1/data/en_US/championFull.json"
        }
        var inputAsString = ""
        try{
            val url = URL(urlString)
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

    companion object{
        const val ITEM_OBJECT = "ITEM"
        const val CHAMPION_OBJECT = "CHAMPION"
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
}