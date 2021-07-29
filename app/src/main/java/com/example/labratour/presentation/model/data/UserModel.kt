package com.example.labratour.presentation.model.data

data class UserModel(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val userName: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: Long = 0,
    val gender: String = "",
    val profileCompleted: Int = 0
)