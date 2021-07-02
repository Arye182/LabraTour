package com.example.labratour.presentation.di

import com.example.labratour.data.repositories.UserRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserAuthViewModelFactory
import com.example.labratour.presentation.viewmodel.UserHomeViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseContainer {
    // instantiate firebase firestore - this is the database and the auth
    val firebaseAuth = FirebaseAuth.getInstance()
    // val firebaseFirestore = FirebaseFirestore.getInstance()
    // create user repo
    val userRepo = UserRepositoryImpl(firebaseAuth)
    // create user usecases
    val loginUseCase = LogInUseCase(userRepo, JobExecutor(), UIThread())
    val registerNewUserUseCase = RegisterNewUserUseCase(userRepo, JobExecutor(), UIThread())
    // ViewModel Factories
    val userAuthViewModelFactory = UserAuthViewModelFactory(loginUseCase, registerNewUserUseCase)
    val userDataViewModelFactory = UserHomeViewModelFactory()
}
