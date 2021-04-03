package com.scp.leagueofquiz.api.database.champion

data class ChampionRoot(
        val type: String,
        val format: String,
        val version: String,
        val data: Map<String, Champion>,
        val keys: Map<String, String>
)