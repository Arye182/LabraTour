package com.example.labratour.presentation.model.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.labratour.presentation.model.dao.UserDao
import com.example.labratour.presentation.model.data.UserModel
import javax.inject.Singleton

@Database(entities = [UserModel:: class], version = 1)
@Singleton
abstract class UsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
