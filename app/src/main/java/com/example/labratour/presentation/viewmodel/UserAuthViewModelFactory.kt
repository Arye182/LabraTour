package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase

/**
 * Login fragment view model factory
 *
 * @property useCase
 * @constructor Create empty Login fragment view model factory
 */
class UserAuthViewModelFactory(private val loginUseCase: LogInUseCase, private val registerNewUserUseCase: RegisterNewUserUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAuthViewModel::class.java)) {
            return UserAuthViewModel(loginUseCase, registerNewUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
