package com.example.labratour.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.labratour.presentation.models.UserModel
import com.example.labratour.presentation.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserHomeViewModel : ViewModel() {

    lateinit var user: UserModel

    val currentUserId: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // TODO - this function should be in the data area
    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        this.currentUserId.postValue(currentUserID)
        return currentUserID
    }

    // TODO - this function should be in the data area
    fun getUserDetails() {
        FirebaseFirestore.getInstance().collection(Constants.USERS).document(getCurrentUserID()).get().addOnSuccessListener { document ->
            val User = document.toObject(UserModel::class.java)!!
        }
    }
}
