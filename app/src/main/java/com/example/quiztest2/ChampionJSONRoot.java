package com.example.quiztest2;

import java.util.HashMap;

public class ChampionJSONRoot {
    private String type;
    private String format;
    private String version;
    private HashMap<String,ChampionJSON> data;
    private HashMap<String,String> keys;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, ChampionJSON> getData() {
        return data;
    }

    public void setData(HashMap<String, ChampionJSON> data) {
        this.data = data;
    }

    public HashMap<String, String> getKeys() {
        return keys;
    }

    public void setKeys(HashMap<String, String> keys) {
        this.keys = keys;
    }
}

