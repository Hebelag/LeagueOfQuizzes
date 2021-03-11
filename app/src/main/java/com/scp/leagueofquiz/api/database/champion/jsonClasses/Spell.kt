package com.scp.leagueofquiz.api.database.champion.jsonClasses

data class Spell (val cooldown: ArrayList<Float>,
                  val cooldownBurn: String,
                  val cost: ArrayList<Float>,
                  val costBurn: String,
                  val costType: String,
                  val description: String,
                  val effect: ArrayList<ArrayList<Float>>,
                  val effectBurn: ArrayList<String>,
                  val id: String,
                  val leveltip: Leveltip,
                  val maxammo: String,
                  val maxrank: Byte,
                  val name: String,
                  val range: ArrayList<Float>,
                  val rangeBurn: String,
                  val resource: String,
                  val tooltip: String
)