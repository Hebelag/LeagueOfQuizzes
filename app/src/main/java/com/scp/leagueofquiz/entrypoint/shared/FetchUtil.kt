package com.scp.leagueofquiz.entrypoint.shared

import com.scp.leagueofquiz.api.database.shared.Image
import com.scp.leagueofquiz.repository.ChampionRepository
import com.scp.leagueofquiz.repository.ItemRepository
import javax.inject.Inject

class FetchUtil @Inject constructor() {
    suspend fun fetchItem(itemRepository: ItemRepository): Pair<String, Image> {
        val item = itemRepository.getRandomItem()
        return Pair(item.name, item.image)
    }

    suspend fun fetchAbility(championRepository: ChampionRepository): Pair<String, Image> {
        val ability = championRepository.getRandomAbility()
        return Pair(ability.name, ability.image)
    }

    suspend fun fetchChampion(championRepository: ChampionRepository): Pair<String, Image> {
        val champion = championRepository.getRandomChampion(emptySet())
        return Pair(champion.name, champion.image)
    }
}