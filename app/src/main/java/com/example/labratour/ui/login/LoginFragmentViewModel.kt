package com.example.labratour.ui.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope

import useCases.DefaultObserver
import useCases.LogInUseCase



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginFragmentViewModel (private val loginUseCase: LogInUseCase) : ViewModel() {
    // args
    private lateinit var password : String
    private lateinit var email : String
    // live data
    val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    // Create a LiveData with a String
    val error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val success: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    // observes the use case work!
    private inner class LogInObserver : DefaultObserver<Void>() {
        //private val mypb : ProgressBar = ProgressBar()
        override fun  onComplete(){
            isLoading.value = false
            success.value = true
        }
        override fun  onError(exception : Throwable ){
            isLoading.value = false
            success.value = false
            error.value = exception.message.toString()
        }
        fun  onNext(){
        }
    }

    // activate the use case of loading!
    fun login(email: String, password: String){
        this.email = email
        this.password = password
        this.isLoading.value = true
        this.loginUseCase.execute(LogInObserver(), email, password)
    }
}

