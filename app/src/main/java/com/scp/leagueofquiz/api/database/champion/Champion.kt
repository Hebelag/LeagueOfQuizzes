package com.scp.leagueofquiz.api.database.champion

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Champion(@PrimaryKey(autoGenerate = true) val id: Int = 0, val identifier: String, val name: String) {
    companion object {
        private const val DEFAULT_CHAMPION_ID = "defaultchampion"
        private const val DEFAULT_CHAMPION_NAME = "Default Champion"
        @JvmField
        val DEFAULT = Champion(identifier = DEFAULT_CHAMPION_ID, name = DEFAULT_CHAMPION_NAME)
    }
}