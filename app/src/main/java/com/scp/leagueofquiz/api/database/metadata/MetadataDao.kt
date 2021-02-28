package com.scp.leagueofquiz.api.database.metadata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.google.common.util.concurrent.ListenableFuture

@Dao
interface MetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(metadata: Metadata?): ListenableFuture<Void?>?

    @Query("SELECT * FROM metadata")
    fun findAll(): ListenableFuture<List<Metadata?>?>?
}