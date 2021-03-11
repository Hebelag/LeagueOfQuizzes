package com.scp.leagueofquiz.api.database.champion

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Info
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Passive
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Skin
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Spell

class Converters {

    @TypeConverter
    fun arrayToString(stringList: List<String>): String{
        return stringList.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToArray(stringListString: String): List<String>{
        return stringListString.split(",").map{it}
    }

    @TypeConverter
    fun spellsToString(spellObject: List<Spell>): String{
        return Gson().toJson(spellObject)
    }

    @TypeConverter
    fun gsonStringToSpells(gsonString: String): List<Spell>{
        val turnsType = object : TypeToken<List<Spell>>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun infoToString(infoObject: Info): String{
        return Gson().toJson(infoObject)
    }

    @TypeConverter
    fun gsonStringToInfo(gsonString: String): Info{
        val turnsType = object : TypeToken<Info>(){}.type
        return Gson().fromJson(gsonString,turnsType)
    }

    @TypeConverter
    fun passiveToString(passiveObject: Passive): String{
        return Gson().toJson(passiveObject)
    }

    @TypeConverter
    fun gsonStringToPassive(gsonString: String): Passive{
        val turnsType = object : TypeToken<Passive>(){}.type
        return Gson().fromJson(gsonString,turnsType)
    }

    @TypeConverter
    fun skinsToString(skinsObject: List<Skin>): String{
        return Gson().toJson(skinsObject)
    }

    @TypeConverter
    fun gsonStringToSkins(gsonString: String): List<Skin>{
        val turnsType = object : TypeToken<List<Skin>>(){}.type
        return Gson().fromJson(gsonString,turnsType)
    }

    @TypeConverter
    fun statsToString(statsObject: Map<String,Double>):String{
        return Gson().toJson(statsObject)
    }

    @TypeConverter
    fun gsonStringToStats(gsonString: String): Map<String,Double>{
        val turnsType = object : TypeToken<Map<String,Double>>(){}.type
        return Gson().fromJson(gsonString,turnsType)
    }

}