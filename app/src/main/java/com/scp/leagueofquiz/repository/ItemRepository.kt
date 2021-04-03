package com.scp.leagueofquiz.repository

import com.scp.leagueofquiz.api.database.item.Item
import com.scp.leagueofquiz.api.database.item.ItemDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao){
    suspend fun getRandomItem(): Item {
        return itemDao.findRandomItem()
    }
}