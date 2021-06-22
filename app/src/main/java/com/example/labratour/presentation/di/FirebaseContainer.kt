package com.example.labratour.presentation.di

import com.example.labratour.data.datasource.UserDataSourceFactory
import com.example.labratour.data.repositories.UserRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class FirebaseContainer {
    // create the view model for login fragment manually with factory - we do that in OnCreate Method
    // instantiate firebase
    val firebaseDataBase = FirebaseAuth.getInstance()
    // instantiate data source factory to create the firebase data source
    //val userFirebaseDataSourceFactory = UserDataSourceFactory(firebaseDataBase)
    // create user repo
    val userRepo = UserRepositoryImpl(firebaseDataBase)
    // create login use case
    val loginUseCase = LogInUseCase(userRepo, JobExecutor(), UIThread())
    // userViewModel Factory
    val userViewModelFactory = UserViewModelFactory(loginUseCase)
}
