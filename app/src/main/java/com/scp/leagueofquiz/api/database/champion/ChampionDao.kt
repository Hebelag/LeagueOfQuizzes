package com.scp.leagueofquiz.api.database.champion

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChampionDao {
    @Insert
    suspend fun insertAll(champions: List<Champion?>?)

    @Query("SELECT * FROM champion")
    suspend fun findAll(): List<Champion>

    @Query("SELECT * FROM champion WHERE name NOT IN (:names) ORDER BY random() LIMIT :quantity")
    suspend fun findRandomChampsExcept(names: List<String>, quantity: Int): List<Champion>
}