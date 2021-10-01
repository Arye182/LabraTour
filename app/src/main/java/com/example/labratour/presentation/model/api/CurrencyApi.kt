package com.example.labratour.presentation.model.api

import com.example.labratour.presentation.model.data.currency.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {

    // d630dce72ffb56f933d7f05abec89ca4
    // https://api.exchangeratesapi.io/v1/latest?access_key=d630dce72ffb56f933d7f05abec89ca4&format=1

    @GET("latest?access_key=d630dce72ffb56f933d7f05abec89ca4")
    suspend fun getRates(
        @Query("base") base: String
    ): Response<CurrencyResponse>
}
