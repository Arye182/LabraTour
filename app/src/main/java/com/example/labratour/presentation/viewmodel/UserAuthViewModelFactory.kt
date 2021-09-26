package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase
import com.example.labratour.domain.useCases.SaveNewUserToFirebaseUseCase
import com.example.labratour.presentation.model.repositories.UserRepository

/**
 * Login fragment view model factory
 *
 * @property useCase
 * @constructor Create empty Login fragment view model factory
 */
class UserAuthViewModelFactory(
    private val loginUseCase: LogInUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val saveNewUserToFirebaseUseCase: SaveNewUserToFirebaseUseCase,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAuthViewModel::class.java)) {
            return UserAuthViewModel(loginUseCase, registerNewUserUseCase, saveNewUserToFirebaseUseCase, userRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
