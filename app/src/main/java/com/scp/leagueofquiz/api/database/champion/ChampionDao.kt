package com.scp.leagueofquiz.api.database.champion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface ChampionDao {
    @Insert
    fun insertAll(champions: List<Champion?>?)

    @Query("SELECT * FROM champion")
    fun findAll(): ListenableFuture<List<Champion?>?>?

    @Query("SELECT * FROM champion WHERE name NOT IN (:names) ORDER BY random() LIMIT :quantity")
    fun findRandomChampsExcept(names: List<String?>?, quantity: Int): ListenableFuture<List<Champion?>?>?
}