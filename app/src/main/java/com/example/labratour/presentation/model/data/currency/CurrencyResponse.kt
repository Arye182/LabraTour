package com.example.labratour.presentation.model.data.currency

data class CurrencyResponse(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)