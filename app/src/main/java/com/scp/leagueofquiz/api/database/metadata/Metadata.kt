package com.scp.leagueofquiz.api.database.metadata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Metadata(@PrimaryKey(autoGenerate = true) val id: Int = 0, val championDataTimestamp: Long)