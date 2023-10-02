package com.mammuten.spliteasy.data.model.entity.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun loadUserById(id: Int): User

//    @Query("SELECT * FROM users WHERE id = :id")
//    fun loadUserById(id: Int): Flow<User>
}