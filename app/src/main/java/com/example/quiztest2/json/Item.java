package com.example.quiztest2.json;

public class Item {
  private String id;
  private Byte count;
  private Boolean hideCount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Byte getCount() {
    return count;
  }

  public void setCount(Byte count) {
    this.count = count;
  }

  public Boolean getHideCount() {
    return hideCount;
  }

  public void setHideCount(Boolean hideCount) {
    this.hideCount = hideCount;
  }
}
