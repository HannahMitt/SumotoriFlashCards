package com.mittens.flashrikishi.api

import com.mittens.flashrikishi.models.WrappedRikishi
import com.mittens.flashrikishi.models.WrappedRikishiList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SumoService {

    @GET("banzuke/0.0.1")
    fun banzuke(@Query("wrapAPIKey") apiKey: String): Call<WrappedRikishiList>

    @GET("rikishi/0.0.2")
    fun rikishi(@Query("id") id: Int, @Query("wrapAPIKey") apiKey: String): Call<WrappedRikishi>
}