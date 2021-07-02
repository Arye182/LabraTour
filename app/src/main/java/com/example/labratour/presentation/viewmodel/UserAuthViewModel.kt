package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labratour.domain.Entity.UserDomain
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.domain.useCases.RegisterNewUserUseCase
import com.example.labratour.presentation.models.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Login fragment view model
 *
 * @property loginUseCase
 * @constructor Create empty Login fragment view model
 */
class UserAuthViewModel(private val loginUseCase: LogInUseCase, private val registerNewUserUseCase: RegisterNewUserUseCase) : ViewModel() {

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

    val signUpFirestoreTaskStatus: MutableLiveData<Boolean> by lazy {
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

    private inner class LogInObserver : DefaultObserver<UserDomain>() {
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
            Log.i("Firebase", "RegisterNewUserObserver - On Next...$id")
            registerNewUserTaskStatus.postValue(true)
        }
    }

    /**
     * Login
     * activate the use case of log in the user
     * @param email
     * @param password
     */
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

    fun signUpToFireStore(email: String, password: String, firstName: String, lastName: String, userName: String) {
        Log.i("Firebase", "Sign Up - User View Model - trying to sign up")
        this.isLoading.postValue(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                // if registration succeeded
                if (task.isSuccessful) {
                    isLoading.postValue(false)
                    signUpFirestoreTaskStatus.postValue(true)
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    userModel = UserModel(firebaseUser.uid, firstName, lastName, userName, email)
                    Log.i("Firebase", "Sign Up - User View Model - sign up successful, user: $firebaseUser")
                } else {
                    // registration failed
                    Log.i("Firebase", "Sign Up - User View Model - sign up failed: " + task.exception!!.message.toString())
                    isLoading.postValue(false)
                    signUpFirestoreTaskStatus.postValue(false)
                    error.postValue(task.exception!!.message.toString())
                }
            }
    }

    fun forgotPassword() {
    }

    fun logOut() {
    }
}
