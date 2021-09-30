package com.example.labratour.presentation.di

import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.api.CurrencyApi
import com.example.labratour.presentation.model.repositories.CurrencyRepositoryImpl
import com.example.labratour.presentation.utils.Dispatchers
import com.example.labratour.presentation.viewmodel.CurrencyViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

class CurrencyContainer(labratourApplication: LabratourApplication) {

    private val BASE_URL = "https://api.exchangeratesapi.io/v1/"

    @Singleton
    val currencyApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApi::class.java)

    @Singleton
    val repo: CurrencyRepositoryImpl = CurrencyRepositoryImpl(currencyApi)

    @Singleton
    val currencyViewModelFactory = CurrencyViewModelFactory(repo, Dispatchers)
}
