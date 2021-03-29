package com.scp.leagueofquiz.repository

import retrofit2.Call
import retrofit2.http.GET

interface DownloadFiles {

    @GET("cdn/11.6.1/data/en_US/championFull.json")
    fun getChampionFull(): Call<String>

}