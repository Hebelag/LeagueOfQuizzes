package com.scp.leagueofquiz.api.database.item.jsonClasses

data class Gold(
        val base: Int,
        val total: Int,
        val sell: Int,
        val purchasable: Boolean
)