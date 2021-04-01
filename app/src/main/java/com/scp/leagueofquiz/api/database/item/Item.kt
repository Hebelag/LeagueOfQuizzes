package com.scp.leagueofquiz.api.database.item

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.scp.leagueofquiz.api.database.item.jsonClasses.Effect
import com.scp.leagueofquiz.api.database.item.jsonClasses.Gold
import com.scp.leagueofquiz.api.database.item.jsonClasses.Image

@Entity
data class Item (
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,
        val identifier: String = "",
        val name: String,
        val gold: Gold,
        val group: String? = "",
        val description: String? = "",
        val colloq: String? = "",
        val plaintext: String? = "",
        val consumed: Boolean? = null,
        val from: List<String>? = listOf(),
        val into: List<String>? = listOf(),
        val stacks: Int? = 0,
        val depth: Int? = 0,
        val effect: Effect? = Effect(),
        val consumeOnFull: Boolean? = null,
        val specialRecipe: Int? = 0,
        val inStore: Boolean? = null,
        val hideFromAll: Boolean? = null,
        val requiredChampion: String? = "",
        val requiredAlly: String? = "",
        val stats: Map<String, Double>? = mapOf(),
        val tags: List<String>,
        val maps: Map<String, Boolean>,
        val image: Image = Image()
        ){
}