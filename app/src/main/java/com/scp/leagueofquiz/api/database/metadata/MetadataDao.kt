package com.scp.leagueofquiz.api.database.metadata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(metadata: Metadata)

    @Query("SELECT * FROM metadata")
    suspend fun findAll(): List<Metadata>
}