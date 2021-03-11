package com.scp.leagueofquiz.api.database

import com.scp.leagueofquiz.api.database.champion.jsonClasses.Info
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Passive
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Skin
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Spell

data class ChampionJSON(
        val id: String,
        val key: String,
        val name: String,
        val title: String,
        val skins: List<Skin>,
        val lore: String,
        val blurb: String,
        val allytips: List<String>,
        val enemytips: List<String>,
        val tags: List<String>,
        val partype: String,
        val info: Info,
        val stats: Map<String, Double>,
        val spells: List<Spell>,
        val passive: Passive
        //, val recommended: Recommended
)