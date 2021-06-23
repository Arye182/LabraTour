package com.example.labratour.presentation.di

import com.example.labratour.data.repositories.UserRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseContainer {
    // instantiate firebase firestore - this is the database
    val firebaseFirestore = FirebaseFirestore.getInstance()
    // instatiate the firebase authenticator for log in
    val firebaseAuth = FirebaseAuth.getInstance()
    // create user repo
    val userRepo = UserRepositoryImpl(firebaseAuth)
    // create user usecases
    val loginUseCase = LogInUseCase(userRepo, JobExecutor(), UIThread())
    // userViewModel Factory
    val userViewModelFactory = UserViewModelFactory(loginUseCase)
}
