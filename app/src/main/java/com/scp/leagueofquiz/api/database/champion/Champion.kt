package com.scp.leagueofquiz.api.database.champion

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Info
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Passive
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Skin
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Spell

@Suppress("EqualsOrHashCode")
@Entity
data class Champion(
        @PrimaryKey(autoGenerate = true) @SerializedName("countId")
        val id: Int = 0,
        @SerializedName("id")
        val identifier: String,
        val name: String,
        val allytips: List<String> = arrayListOf(),
        val blurb: String = "",
        val enemytips: List<String> = arrayListOf(),
        val info: Info = Info(),
        val key: String = "",
        val lore: String = "",
        val partype: String = "",
        val passive: Passive = Passive(),
        val skins: List<Skin> = arrayListOf(),
        //val recommended: Recommended,
        val spells: List<Spell> = arrayListOf(),
        val stats: Map<String, Double> = mapOf(),
        val tags: List<String> = arrayListOf(),
        val title: String = ""
) {
    companion object {
        private const val DEFAULT_CHAMPION_ID = "defaultchampion"
        private const val DEFAULT_CHAMPION_NAME = "Default Champion"
        @JvmField
        val DEFAULT = Champion(
                identifier = DEFAULT_CHAMPION_ID,
                name = DEFAULT_CHAMPION_NAME
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Champion

        if (identifier != other.identifier) return false
        if (name != other.name) return false
//        if (allytips != other.allytips) return false
//        if (blurb != other.blurb) return false
//        if (enemytips != other.enemytips) return false
//        if (info != other.info) return false
//        if (key != other.key) return false
//        if (lore != other.lore) return false
//        if (partype != other.partype) return false
//        if (passive != other.passive) return false
//        if (skins != other.skins) return false
//        if (spells != other.spells) return false
//        if (stats != other.stats) return false
//        if (tags != other.tags) return false
//        if (title != other.title) return false

        return true
    }
}