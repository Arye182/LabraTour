package com.example.labratour.presentation.model.repositories

import com.example.labratour.presentation.model.data.CurrencyResponse
import com.example.labratour.presentation.utils.Resource

interface CurrencyRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>
}
