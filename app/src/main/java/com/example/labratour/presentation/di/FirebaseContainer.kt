package com.example.labratour.presentation.di

import com.example.labratour.data.repositories.UserRepositoryImpl
import com.example.labratour.data.utils.JobExecutor
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase
import com.example.labratour.domain.useCases.SaveNewUserToFirebaseUseCase
import com.example.labratour.presentation.LabratourApplication
import com.example.labratour.presentation.model.repositories.UserRepository
import com.example.labratour.presentation.utils.UIThread
import com.example.labratour.presentation.viewmodel.UserAuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Singleton

// import com.google.firebase.firestore.FirebaseFirestore

class FirebaseContainer(labratourApplication: LabratourApplication, userCacheRepository: UserRepository) {
    // instantiate firebase firestore - this is the database and the auth
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()
    // val firebaseFirestore = FirebaseFirestore.getInstance()

    // create user repo
    @Singleton
    val userRepo = UserRepositoryImpl(firebaseAuth, firebaseDatabase)
    // create user usecases
    @Singleton
    val loginUseCase = LogInUseCase(userRepo, JobExecutor(), UIThread())
    @Singleton
    val registerNewUserUseCase = RegisterNewUserUseCase(userRepo, JobExecutor(), UIThread())
    @Singleton
    val saveNewUserToFirebaseUseCase = SaveNewUserToFirebaseUseCase(userRepo, JobExecutor(), UIThread())
    // ViewModel Factories
    @Singleton
    val userAuthViewModelFactory = UserAuthViewModelFactory(loginUseCase, registerNewUserUseCase, saveNewUserToFirebaseUseCase, userCacheRepository)
}
