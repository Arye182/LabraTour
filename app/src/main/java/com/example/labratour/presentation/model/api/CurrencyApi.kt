package com.example.labratour.presentation.model.api

import com.example.labratour.presentation.model.data.currency.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    @GET("latest?")
    suspend fun getRates(
        @Query("base") base: String,
        @Query("access_key") key: String
    ): Response<CurrencyResponse>
}
