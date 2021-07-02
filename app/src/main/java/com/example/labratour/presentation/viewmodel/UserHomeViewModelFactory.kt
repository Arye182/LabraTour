package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserHomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserHomeViewModel::class.java)) {
            return UserHomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
