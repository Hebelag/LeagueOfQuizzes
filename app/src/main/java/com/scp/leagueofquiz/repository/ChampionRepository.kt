package com.scp.leagueofquiz.repository

import com.google.common.util.concurrent.ListenableFuture
import com.scp.leagueofquiz.api.database.champion.Champion
import com.scp.leagueofquiz.api.database.champion.ChampionDao
import java.util.stream.Collectors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChampionRepository @Inject constructor(private val championDao: ChampionDao) {
    fun getRandomChampions(
            championsAnswered: Set<Champion>, howMany: Int): ListenableFuture<List<Champion?>?>? {
        val namesAnswered = championsAnswered.map { it.name }
        return championDao.findRandomChampsExcept(namesAnswered, howMany)
    }

    fun getAllChampions(): List<Champion?>?{
        return championDao.findAll()
    }
}