package com.example.labratour.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labratour.domain.useCases.DefaultObserver
import com.example.labratour.domain.useCases.LogInUseCase

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

    /**
     * Log in observer
     *
     * @constructor Create empty Log in observer
     */
    private inner class LogInObserver : DefaultObserver<Void>() {
        /**
         * On complete
         *
         */
        override fun onComplete() {
            Log.i("Firebase", "Log In Observer - On Complete...")
            isLoading.value = false
            logInTaskStatus.value = true
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
        fun onNext() {
            Log.i("Firebase", "Log In Observer - On Next...")
        }
    }

    /**
     * Login
     * activate the use case of log in the user
     * @param email
     * @param password
     */
    fun login(email: String, password: String) {
        this.isLoading.postValue(true)
        this.loginUseCase.execute(LogInObserver(), email, password)
    }

    fun signUp(email: String, password: String) {
        this.isLoading.postValue(true)
    }

    fun forgotPassword() {
    }

    fun logOut() {
    }
}
