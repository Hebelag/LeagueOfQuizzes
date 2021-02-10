package com.scp.leagueofquiz.api.database.champion;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

@Entity
public class Champion {
  private static final String DEFAULT_CHAMPION_ID = "defaultchampion";
  private static final String DEFAULT_CHAMPION_NAME = "Default Champion";
  public static final Champion DEFAULT = new Champion(DEFAULT_CHAMPION_ID, DEFAULT_CHAMPION_NAME);

  @PrimaryKey(autoGenerate = true)
  public int id;

  public String identifier;
  public String name;

  public Champion() {}

  public Champion(String identifier, String name) {
    this.identifier = identifier;
    this.name = name;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Champion champion = (Champion) o;
    return Objects.equals(identifier, champion.identifier) && Objects.equals(name, champion.name);
  }

  @NotNull
  @Override
  public String toString() {
    return "Champion{" + "identifier='" + identifier + '\'' + ", name='" + name + '\'' + '}';
  }
}
