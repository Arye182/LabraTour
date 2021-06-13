package com.example.labratour.ui.login.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.useCases.DefaultObserver
import com.example.useCases.LogInUseCase


class LoginFragmentViewModel (private val loginUseCase: LogInUseCase) : ViewModel() {
    //private val loginUseCase: LogInUseCase
    // args
    private lateinit var password : String
    private lateinit var email : String
    // live data
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    // this is a string of error message that returned from log in task
    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    // this indicates if log in was success or not
    val success: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    // observes the use case - log in
    private inner class LogInObserver : DefaultObserver<Void>() {
        override fun  onComplete(){
            isLoading.value = false
            success.value = true
        }
        override fun  onError(exception : Throwable ){
            isLoading.postValue(false)
            success.postValue(false)
            error.postValue(exception.message)
        }
        fun  onNext(){
        }
    }

    // activate the use case of log in the user
    fun login(email: String, password: String){
        this.email = email
        this.password = password
        this.isLoading.postValue(true)
        this.loginUseCase.execute(LogInObserver(), email, password)
    }
}

