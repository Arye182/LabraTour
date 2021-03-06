package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labratour.domain.Entity.UserDomain
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase
import com.example.labratour.domain.useCases.SaveNewUserToFirebaseUseCase
import com.example.labratour.presentation.mappers.userModelTouserDomain
import com.example.labratour.presentation.model.data.UserModel
import com.example.labratour.presentation.model.repositories.UserRepository
import kotlinx.coroutines.launch

// import com.google.firebase.auth.FirebaseAuth
// import com.google.firebase.auth.FirebaseUser

/**
 * Login fragment view model
 *
 * @property loginUseCase
 * @constructor Create empty Login fragment view model
 */
class
UserAuthViewModel(
    private val loginUseCase: LogInUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val saveNewUserToFirebaseUseCase: SaveNewUserToFirebaseUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    // live data
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    // this is a string of error message that returned from log in task
    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // this indicates if log in was success or not
    val logInTaskStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val registerNewUserTaskStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val saveNewUserToFirebaseTaskStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    lateinit var userModel: UserModel
    lateinit var userDomain: UserDomain
    var userID: String = ""

    private inner class LogInObserver :
        DefaultObserver<UserDomain>() {
        override fun onComplete() {
            Log.i("Firebase", "Log In Observer - On Complete...")
            isLoading.postValue(false)
        }
        override fun onError(exception: Throwable) {
            Log.i("Firebase", "Log In Observer - On Error: " + exception.message)
            isLoading.postValue(false)
            logInTaskStatus.postValue(false)
            error.postValue(exception.message)
        }
        override fun onNext(value: UserDomain) {
            Log.i("Firebase", "Log In Observer - On Next...")
            userDomain = value
            logInTaskStatus.postValue(true)
        }
    }

    private inner class RegisterNewUserObserver : DefaultObserver<String>() {
        override fun onComplete() {
            Log.i("Firebase", "RegisterNewUserObserver - On Complete...")
            isLoading.postValue(false)
        }
        override fun onError(exception: Throwable) {
            Log.i("Firebase", "RegisterNewUserObserver - On Error: " + exception.message)
            isLoading.postValue(false)
            registerNewUserTaskStatus.postValue(false)
            error.postValue(exception.message)
        }
        override fun onNext(id: String) {
            userID = id
            Log.i("Firebase", "RegisterNewUserObserver - On Next...$id")
            isLoading.postValue(false)
            registerNewUserTaskStatus.postValue(true)
        }
    }

    private inner class SaveNewUserToFirebaseUseCaseObserver : DefaultObserver<String>() {
        override fun onComplete() {
            Log.i("Firebase", "SaveNewUserToFirebaseUseCase - On Complete...")
            isLoading.postValue(false)
        }
        override fun onError(exception: Throwable) {
            Log.i("Firebase", "SaveNewUserToFirebaseUseCase - On Error: " + exception.message)
            isLoading.postValue(false)
            saveNewUserToFirebaseTaskStatus.postValue(false)
            error.postValue(exception.message)
        }
        override fun onNext(v: String) {
            Log.i("Firebase", "SaveNewUserToFirebaseUseCase - On Next...")
            isLoading.postValue(false)
            saveNewUserToFirebaseTaskStatus.postValue(true)
        }
    }

    fun login(email: String, password: String) {
        Log.i("Firebase", "User View Model - Login - activating login usecase")
        this.isLoading.postValue(true)
        this.loginUseCase.execute(LogInObserver(), email, password)
    }

    fun registerNewUser(email: String, password: String) {
        Log.i("Firebase", "User View Model - RegisterNewUser - activating registerNewUser usecase")
        this.isLoading.postValue(true)
        this.registerNewUserUseCase.execute(RegisterNewUserObserver(), email, password)
    }

    fun saveToDataBaseNewUser(email: String, firstName: String, lastName: String, userName: String) {
        Log.i("Firebase", "User View Model - saveToDataBaseNewUser - activating saveToDataBaseNewUser usecase")
        userModel = UserModel(userID, firstName, lastName, userName, email)
        userDomain = userModelTouserDomain().toDomain(userModel, UserDomain(userID))
        this.isLoading.postValue(true)
        this.saveNewUserToFirebaseUseCase.execute(SaveNewUserToFirebaseUseCaseObserver(), userDomain)
    }

    fun saveToCahceDataBaseNewUser(email: String, firstName: String, lastName: String, userName: String) {
        viewModelScope.launch {
            userRepository.insertUser(UserModel(userID, firstName, lastName, userName, email, "", 0, "", 0))
        }
    }

    fun forgotPassword() {
    }

    fun logOut() {
    }
}
