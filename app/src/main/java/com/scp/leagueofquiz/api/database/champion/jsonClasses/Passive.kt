package com.scp.leagueofquiz.api.database.champion.jsonClasses

import com.scp.leagueofquiz.api.database.shared.Image

data class Passive(val description: String = "",
                   val name: String = "",
                   val image: Image = Image())