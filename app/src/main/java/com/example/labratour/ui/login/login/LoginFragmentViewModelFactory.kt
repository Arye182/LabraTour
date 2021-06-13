package com.example.labratour.ui.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.useCases.LogInUseCase

/**
 * Login fragment view model factory
 *
 * @property useCase
 * @constructor Create empty Login fragment view model factory
 */
class LoginFragmentViewModelFactory(private val useCase: LogInUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)) {
            return LoginFragmentViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
