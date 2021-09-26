package com.example.labratour.presentation.model.repositories

import com.example.labratour.presentation.model.dao.UserDao
import com.example.labratour.presentation.model.data.UserModel

class UserRepository(private val dao: UserDao) {

    suspend fun getUser(id: String): UserModel {
        return dao.getUser(id)
    }

    // insert new user (save)
    suspend fun insertUser(user: UserModel) {
        dao.insertUser(user)
    }

    // delete a spesific user
    suspend fun deleteUser(user_id: String) {
        dao.deleteUser(user_id)
    }
}
