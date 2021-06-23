package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labratour.domain.Entity.User
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.LogInUseCase
import com.example.labratour.presentation.models.UserView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Login fragment view model
 *
 * @property loginUseCase
 * @constructor Create empty Login fragment view model
 */
class UserViewModel(private val loginUseCase: LogInUseCase) : ViewModel() {

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

    val signUpTaskStatus: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    lateinit var user: UserView

    /**
     * Log in observer
     *
     * @constructor Create empty Log in observer
     */
    private inner class LogInObserver : DefaultObserver<UserView>() {
        /**
         * On complete
         *
         */
        override fun onComplete() {
            Log.i("Firebase", "Log In Observer - On Complete...")
            isLoading.postValue(false)
        }

        /**
         * On error
         *
         * @param exception
         */
        override fun onError(exception: Throwable) {
            Log.i("Firebase", "Log In Observer - On Error: " + exception.message)
            isLoading.postValue(false)
            logInTaskStatus.postValue(false)
            error.postValue(exception.message)
        }

        /**
         * On next
         *
         */
        override fun onNext(value: UserView) {
            Log.i("Firebase", "Log In Observer - On Next...")
            logInTaskStatus.postValue(true)
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

    fun signUp(email: String, password: String, firstName: String, lastName: String, userName: String) {
        Log.i("Firebase", "Sign Up - User View Model - trying to sign up")
        this.isLoading.postValue(true)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                // if registration succeeded
                if (task.isSuccessful) {
                    isLoading.postValue(false)
                    signUpTaskStatus.postValue(true)
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    user = UserView(firebaseUser.uid, firstName, lastName, userName, email)
                    Log.i("Firebase", "Sign Up - User View Model - sign up successful, user: $firebaseUser")
                } else {
                    // registration failed
                    Log.i("Firebase", "Sign Up - User View Model - sign up failed: " + task.exception!!.message.toString())
                    isLoading.postValue(false)
                    signUpTaskStatus.postValue(false)
                    error.postValue(task.exception!!.message.toString())
                }
            }
    }

    fun forgotPassword() {
    }

    fun logOut() {
    }
}
