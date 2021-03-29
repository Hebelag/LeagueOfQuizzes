package com.scp.leagueofquiz.api.database.item

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scp.leagueofquiz.api.database.item.jsonClasses.Gold
import com.scp.leagueofquiz.api.database.item.jsonClasses.Rune

@Entity
data class Item (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val name: String,
        val rune: Rune,
        val gold: Gold,
        val group: String,
        val description: String,
        val colloq: String,
        val plaintext: String,
        val consumed: Boolean,
        val from: List<String>,
        val into: List<String>,
        val stacks: Int,
        val depth: Int,
        val consumeOnFull: Boolean,
        val specialRecipe: Int,
        val inStore: Boolean,
        val hideFromAll: Boolean,
        val requiredChampion: String,
        val requiredAlly: String,
        val stats: Map<String, Int>,
        val tags: List<String>,
        val maps: Map<String, Boolean>
        ){
}