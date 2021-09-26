package com.example.labratour.presentation.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.labratour.presentation.model.data.UserModel

@Dao
interface UserDao {

    // get current user data
    @Query("SELECT * FROM users Where id = :user_id")
    fun getUser(user_id: String): UserModel

    // insert new user (save)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    // delete a spesific user
    @Query("DELETE FROM users Where id = :user_id")
    suspend fun deleteUser(user_id: String)

    // update a specific user
}
