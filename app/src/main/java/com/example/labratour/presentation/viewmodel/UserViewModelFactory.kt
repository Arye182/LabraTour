package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.domain.useCases.LogInUseCase

/**
 * Login fragment view model factory
 *
 * @property useCase
 * @constructor Create empty Login fragment view model factory
 */
class UserViewModelFactory(private val loginUseCase: LogInUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
