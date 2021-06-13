package com.example.labratour.ui.login.login

import com.example.useCases.LogInUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class LoginFragmentViewModelFactory (private val useCase : LogInUseCase) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginFragmentViewModel::class.java)){
            return LoginFragmentViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}