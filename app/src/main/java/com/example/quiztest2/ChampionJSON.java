package com.example.quiztest2;

import java.util.HashMap;

public class ChampionJSON {
    private String id;
    private String key;
    private String name;
    private String title;
    private Image image;
    private Skins[] skins;
    private String lore;
    private String blurb;
    private String[] allytips;
    private String[] enemytips;
    private String[] tags;
    private String partype;
    private Info info;
    private HashMap<String,Double> stats;
    private Spell[] spells;
    private Passive passive;
    private Recommended[] recommended;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Skins[] getSkins() {
        return skins;
    }

    public void setSkins(Skins[] skins) {
        this.skins = skins;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String[] getAllytips() {
        return allytips;
    }

    public void setAllytips(String[] allytips) {
        this.allytips = allytips;
    }

    public String[] getEnemytips() {
        return enemytips;
    }

    public void setEnemytips(String[] enemytips) {
        this.enemytips = enemytips;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public HashMap<String, Double> getStats() {
        return stats;
    }

    public void setStats(HashMap<String, Double> stats) {
        this.stats = stats;
    }

    public Spell[] getSpells() {
        return spells;
    }

    public void setSpells(Spell[] spells) {
        this.spells = spells;
    }

    public Passive getPassive() {
        return passive;
    }

    public void setPassive(Passive passive) {
        this.passive = passive;
    }

    public Recommended[] getRecommended() {
        return recommended;
    }

    public void setRecommended(Recommended[] recommended) {
        this.recommended = recommended;
    }
}
