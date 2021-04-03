package com.scp.leagueofquiz.api

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.scp.leagueofquiz.api.database.LolDatabase
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import com.scp.leagueofquiz.api.database.item.ItemDao
import com.scp.leagueofquiz.api.database.metadata.MetadataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideLolDatabase(@ApplicationContext applicationContext: Context): LolDatabase {
        return Room.databaseBuilder(applicationContext, LolDatabase::class.java, DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun provideChampionDao(db: LolDatabase): ChampionDao {
        return db.championDao()
    }

    @Singleton
    @Provides
    fun provideItemDao(db: LolDatabase): ItemDao {
        return db.itemDao()
    }


    @Singleton
    @Provides
    fun provideMetadataDao(db: LolDatabase): MetadataDao {
        return db.metadataDao()
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    companion object {
        private const val DATABASE_NAME = "lol-database"
    }
}