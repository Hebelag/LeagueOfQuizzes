package com.scp.leagueofquiz.api.database.metadata;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;

@Dao
public interface MetadataDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  ListenableFuture<Void> insert(Metadata metadata);

  @Query("SELECT * FROM metadata")
  ListenableFuture<List<Metadata>> findAll();
}
