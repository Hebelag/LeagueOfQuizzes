package com.scp.leagueofquiz.api.database.champion.jsonClasses

import com.scp.leagueofquiz.api.database.shared.Image

data class Spell (val cooldown: ArrayList<Float> = arrayListOf(),
                  val cooldownBurn: String = "",
                  val cost: ArrayList<Float> = arrayListOf(),
                  val costBurn: String = "",
                  val costType: String = "",
                  val description: String = "",
                  val effect: ArrayList<ArrayList<Float>> = arrayListOf(),
                  val effectBurn: ArrayList<String> = arrayListOf(),
                  val id: String = "",
                  val image: Image = Image(),
                  val leveltip: Leveltip = Leveltip(),
                  val maxammo: String = "",
                  val maxrank: Byte = 0,
                  val name: String = "",
                  val range: ArrayList<Float> = arrayListOf(),
                  val rangeBurn: String = "",
                  val resource: String = "",
                  val tooltip: String = ""
)