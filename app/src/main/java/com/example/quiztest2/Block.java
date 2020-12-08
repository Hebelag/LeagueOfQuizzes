package com.example.quiztest2;

public class Block {
    private String type;
    private Boolean recMath;
    private Boolean recSteps;
    private Byte minSummonerLevel;
    private Byte maxSummonerLevel;
    private String showIfSummonerSpell;
    private String hideIfSummonerSpell;
    private String appendAfterSection;
    private String[] visibleWithAllOf;
    private String[] hiddenWithAnyOf;
    private Item[] items;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getRecMath() {
        return recMath;
    }

    public void setRecMath(Boolean recMath) {
        this.recMath = recMath;
    }

    public Boolean getRecSteps() {
        return recSteps;
    }

    public void setRecSteps(Boolean recSteps) {
        this.recSteps = recSteps;
    }

    public Byte getMinSummonerLevel() {
        return minSummonerLevel;
    }

    public void setMinSummonerLevel(Byte minSummonerLevel) {
        this.minSummonerLevel = minSummonerLevel;
    }

    public Byte getMaxSummonerLevel() {
        return maxSummonerLevel;
    }

    public void setMaxSummonerLevel(Byte maxSummonerLevel) {
        this.maxSummonerLevel = maxSummonerLevel;
    }

    public String getShowIfSummonerSpell() {
        return showIfSummonerSpell;
    }

    public void setShowIfSummonerSpell(String showIfSummonerSpell) {
        this.showIfSummonerSpell = showIfSummonerSpell;
    }

    public String getHideIfSummonerSpell() {
        return hideIfSummonerSpell;
    }

    public void setHideIfSummonerSpell(String hideIfSummonerSpell) {
        this.hideIfSummonerSpell = hideIfSummonerSpell;
    }

    public String getAppendAfterSection() {
        return appendAfterSection;
    }

    public void setAppendAfterSection(String appendAfterSection) {
        this.appendAfterSection = appendAfterSection;
    }

    public String[] getVisibleWithAllOf() {
        return visibleWithAllOf;
    }

    public void setVisibleWithAllOf(String[] visibleWithAllOf) {
        this.visibleWithAllOf = visibleWithAllOf;
    }

    public String[] getHiddenWithAnyOf() {
        return hiddenWithAnyOf;
    }

    public void setHiddenWithAnyOf(String[] hiddenWithAnyOf) {
        this.hiddenWithAnyOf = hiddenWithAnyOf;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
