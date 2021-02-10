package com.scp.leagueofquiz.api.database.metadata;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class Metadata {
  @PrimaryKey(autoGenerate = true)
  public int id;

  public long championDataTimestamp;

  public Metadata() {}

  public Metadata(long championDataTimestamp) {
    this.championDataTimestamp = championDataTimestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metadata metadata = (Metadata) o;
    return championDataTimestamp == metadata.championDataTimestamp;
  }

  @Override
  public @NotNull String toString() {
    return "Metadata{" + "championDataTimestamp=" + championDataTimestamp + '}';
  }
}
