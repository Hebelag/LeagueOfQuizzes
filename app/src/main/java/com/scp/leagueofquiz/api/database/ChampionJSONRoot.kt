package com.scp.leagueofquiz.api.database

data class ChampionJSONRoot(
        val type: String,
        val format: String,
        val version: String,
        val data: Map<String, ChampionJSON>,
        val keys: Map<String, String>
)