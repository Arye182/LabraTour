package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.presentation.model.repositories.CurrencyRepository
import com.example.labratour.presentation.utils.DispatcherProvider

class CurrencyViewModelFactory(
    private val repository: CurrencyRepository,
    private val dispatchers: DispatcherProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(repository, dispatchers) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
