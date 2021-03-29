package com.scp.leagueofquiz.api.database.item

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.scp.leagueofquiz.api.database.champion.Champion

@Dao
interface ItemDao {
    @Insert
    suspend fun insertAll(items: List<Item?>?)

    @Query("SELECT * FROM item")
    suspend fun findAll(): List<Item>

    @Query("SELECT * FROM item WHERE name ORDER BY random() LIMIT :quantity")
    suspend fun findRandomItems( quantity: Int): List<Item>

    @Query("SELECT * FROM item WHERE name ORDER BY random() LIMIT 1")
    suspend fun findRandomItem(): Item
}