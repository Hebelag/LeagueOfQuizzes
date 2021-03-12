package com.scp.leagueofquiz.api.database

import com.scp.leagueofquiz.api.database.champion.Champion

data class ChampionJSONRoot(
        val type: String,
        val format: String,
        val version: String,
        val data: Map<String, Champion>,
        val keys: Map<String, String>
)