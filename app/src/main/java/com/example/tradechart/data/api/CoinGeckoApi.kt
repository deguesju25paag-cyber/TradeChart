package com.example.tradechart.data.api

import retrofit2.http.GET
import retrofit2.http.Query

// Interfaz para la API de CoinGecko
interface CoinGeckoApi {

    @GET("simple/price")
    suspend fun getPrice(
        @Query("ids") ids: String,
        @Query("vs_currencies") vsCurrencies: String
    ): Map<String, Map<String, Double>>
}
