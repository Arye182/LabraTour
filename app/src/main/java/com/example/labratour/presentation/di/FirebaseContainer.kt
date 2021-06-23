package com.example.labratour.presentation.di

import com.example.labratour.data.repositories.UserRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseContainer {
    // pushing it again
    // create the view model for login fragment manually with factory - we do that in OnCreate Method
    // instantiate firebase
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseFirestore = FirebaseFirestore.getInstance()
    // create user repo
    val userRepo = UserRepositoryImpl(firebaseAuth)
    // create user usecases
    val loginUseCase = LogInUseCase(userRepo, JobExecutor(), UIThread())
    // userViewModel Factory
    val userViewModelFactory = UserViewModelFactory(loginUseCase)
}
