package com.scp.leagueofquiz.entrypoint.shared

import kotlin.random.Random


enum class GameModes {
    TEXTTOPICTURES, PICTURETOTEXTS, SHARPEN, CONNECT;

    companion object{
        fun randomGameMode(): GameModes{
            return values()[Random.nextInt(values().size)]
        }
    }
}