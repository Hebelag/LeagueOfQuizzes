package com.example.quiztest2.json;

public class Recommended {
  private String champion;
  private String title;
  private String map;
  private String mode;
  private String type;
  private String customTag;
  private Integer sortrank;
  private Boolean useObviousCheckmark;
  private Object customPanel;
  private Block[] blocks;

  public String getChampion() {
    return champion;
  }

  public void setChampion(String champion) {
    this.champion = champion;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getMap() {
    return map;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getMode() {
    return mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getCustomTag() {
    return customTag;
  }

  public void setCustomTag(String customTag) {
    this.customTag = customTag;
  }

  public Integer getSortrank() {
    return sortrank;
  }

  public void setSortrank(Integer sortrank) {
    this.sortrank = sortrank;
  }

  public Boolean getUseObviousCheckmark() {
    return useObviousCheckmark;
  }

  public void setUseObviousCheckmark(Boolean useObviousCheckmark) {
    this.useObviousCheckmark = useObviousCheckmark;
  }

  public Object getCustomPanel() {
    return customPanel;
  }

  public void setCustomPanel(Object customPanel) {
    this.customPanel = customPanel;
  }

  public Block[] getBlocks() {
    return blocks;
  }

  public void setBlocks(Block[] blocks) {
    this.blocks = blocks;
  }
}
