package com.scp.leagueofquiz.api.database.item.jsonClasses

import com.google.gson.annotations.SerializedName

data class Group(
        val id: String,
        @SerializedName("MaxGroupOwnable")
        val maxGroupOwnable: String
)