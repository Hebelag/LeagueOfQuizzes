package com.scp.leagueofquiz.entrypoint.shared;

import androidx.annotation.Nullable;

public class QuizChampion {
  private static final String DEFAULT_CHAMPION_ID = "defaultchampion";
  private static final String DEFAULT_CHAMPION_KEY = "1234567890";
  private static final String DEFAULT_CHAMPION_NAME = "Default Champion";
  public static final QuizChampion DEFAULT =
      new QuizChampion(DEFAULT_CHAMPION_ID, DEFAULT_CHAMPION_KEY, DEFAULT_CHAMPION_NAME);

  private final String id;
  private final String key;
  private final String name;

  public QuizChampion(String id, String key, String name) {
    this.id = id;
    this.key = key;
    this.name = name;
  }

  public String getId() {
    return id.toLowerCase();
  }

  public String getKey() {
    return key;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    return obj instanceof QuizChampion && getName().equals(((QuizChampion) obj).getName());
  }
}
