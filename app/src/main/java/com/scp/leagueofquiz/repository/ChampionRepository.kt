package com.scp.leagueofquiz.repository

import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChampionRepository @Inject constructor(private val championDao: ChampionDao) {
    suspend fun getRandomChampions(
            championsAnswered: Set<Champion>, howMany: Int): List<Champion> {
        val namesAnswered = championsAnswered.map { it.name }
        return championDao.findRandomChampsExcept(namesAnswered, howMany)
    }

    suspend fun getAllChampions(): List<Champion> {
        return championDao.findAll()
    }
}