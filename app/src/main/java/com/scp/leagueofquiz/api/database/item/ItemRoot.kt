package com.scp.leagueofquiz.api.database.item

import com.scp.leagueofquiz.api.database.item.jsonClasses.Group
import com.scp.leagueofquiz.api.database.item.jsonClasses.Tree

data class ItemRoot(
        val type: String,
        val version: String,
        val basic: Item,
        val data: Map<String,Item>,
        val groups: List<Group>,
        val tree: List<Tree>
)