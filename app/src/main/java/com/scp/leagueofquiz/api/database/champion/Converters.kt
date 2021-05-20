package com.scp.leagueofquiz.api.database.champion

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.scp.leagueofquiz.api.database.champion.jsonClasses.*
import com.scp.leagueofquiz.api.database.item.jsonClasses.Effect
import com.scp.leagueofquiz.api.database.item.jsonClasses.Gold
import com.scp.leagueofquiz.api.database.shared.Image

class Converters {

    @TypeConverter
    fun arrayToString(stringList: List<String>?): String?{
        return stringList?.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToArray(stringListString: String?): List<String>?{
        return stringListString?.split(",")?.map{it}
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

    @TypeConverter
    fun imageToString(imageObject: Image):String{
        return Gson().toJson(imageObject)
    }

    @TypeConverter
    fun gsonStringToImage(gsonString: String): Image{
        val turnsType = object : TypeToken<Image>(){}.type
        return Gson().fromJson(gsonString,turnsType)
    }

    @TypeConverter
    fun goldToString(goldObject: Gold?): String{
        return Gson().toJson(goldObject)
    }

    @TypeConverter
    fun gsonStringToGold(gsonString: String): Gold? {
        val turnsType = object: TypeToken<Gold>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun itemMapsToString(mapsObject: Map<String, Boolean>): String{
        return Gson().toJson(mapsObject)
    }

    @TypeConverter
    fun gsonStringToItemMaps(gsonString: String): Map<String, Boolean>{
        val turnsType = object: TypeToken<Map<String, Boolean>>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun itemEffectToString(effectObject: Effect?): String{
        return Gson().toJson(effectObject)
    }

    @TypeConverter
    fun gsonStringToItemEffect(gsonString: String): Effect?{
        val turnsType = object: TypeToken<Effect>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun leveltipToString(leveltipObject: Leveltip?): String{
        return Gson().toJson(leveltipObject)
    }

    @TypeConverter
    fun gsonStringToLeveltip(gsonString: String): Leveltip?{
        val turnsType = object: TypeToken<Leveltip>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun floatArrayToString(floatArrayObject: List<Float>): String{
        return Gson().toJson(floatArrayObject)
    }

    @TypeConverter
    fun gsonStringToFloatArray(gsonString: String): List<Float>{
        val turnsType = object: TypeToken<List<Float>>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

    @TypeConverter
    fun arrayFloatArrayToString(arrayFloatArrayObject: List<ArrayList<Float>>): String{
        return Gson().toJson(arrayFloatArrayObject)
    }

    @TypeConverter
    fun gsonStringToArrayFloatArray(gsonString: String): List<ArrayList<Float>>{
        val turnsType = object: TypeToken<List<ArrayList<Float>>>(){}.type
        return Gson().fromJson(gsonString, turnsType)
    }

}