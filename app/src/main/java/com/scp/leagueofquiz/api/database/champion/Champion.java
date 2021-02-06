package com.scp.leagueofquiz.api.database.champion;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity
public class Champion {
  @PrimaryKey(autoGenerate = true)
  public int id;

  public String identifier;
  public String name;

  public Champion() {}

  public Champion(String identifier, String name) {
    this.identifier = identifier;
    this.name = name;
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
