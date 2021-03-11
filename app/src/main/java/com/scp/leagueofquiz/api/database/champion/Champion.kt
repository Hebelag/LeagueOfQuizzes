package com.scp.leagueofquiz.api.database.champion

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Info
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Passive
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Skin
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Spell

@Entity
data class Champion(@PrimaryKey(autoGenerate = true)
                    val id: Int = 0,
                    @SerializedName("id")
                    val identifier: String,
                    val name: String,
                    val allytips: List<String>,
                    val blurb: String,
                    val enemytips: List<String>,
                    val info: Info,
                    val key: String,
                    val lore: String,
                    val partype: String,
                    val passive: Passive,
                    val skins: List<Skin>,
                    //val recommended: Recommended,
                    val spells: List<Spell>,
                    val stats: Map<String, Double>,
                    val tags: List<String>,
                    val title: String
) {
    companion object {
        private const val DEFAULT_CHAMPION_ID = "defaultchampion"
        private const val DEFAULT_CHAMPION_NAME = "Default Champion"
        @JvmField
        val DEFAULT = Champion(
                identifier = DEFAULT_CHAMPION_ID,
                name = DEFAULT_CHAMPION_NAME,
                allytips = arrayListOf(),
                blurb = "",
                enemytips = arrayListOf(),
                info = Info(),
                key = "",
                lore = "",
                partype = "",
                passive = Passive(),
                spells = arrayListOf(),
                stats = mapOf(),
                tags = arrayListOf(),
                title = "",
                skins = arrayListOf()
        )
    }
}