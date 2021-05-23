package com.scp.leagueofquiz.repository

import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import com.scp.leagueofquiz.api.database.champion.jsonClasses.Spell
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChampionRepository @Inject constructor(private val championDao: ChampionDao) {
    suspend fun getRandomChampions(
            championsAnswered: Set<Champion>, howMany: Int): List<Champion> {
        val namesAnswered = championsAnswered.map { it.name }
        return championDao.findRandomChampsExcept(namesAnswered, howMany)
    }

    suspend fun getRandomChampion( championsAnswered: Set<Champion>): Champion{
        val namesAnswered = championsAnswered.map { it.name }
        return championDao.findRandomChampionExcept(namesAnswered)
    }

    suspend fun getRandomAbility(): Spell{
        val randomNumber = (0..3).random()
        val champ = championDao.findRandomAbility()
        val spell = champ.spells[randomNumber]
        return spell
    }

    suspend fun getAllChampions(): List<Champion> {
        return championDao.findAll()
    }
}