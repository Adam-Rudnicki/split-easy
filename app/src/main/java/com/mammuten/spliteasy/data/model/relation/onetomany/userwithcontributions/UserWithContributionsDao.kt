package com.mammuten.spliteasy.data.model.relation.onetomany.userwithcontributions

import androidx.room.Query
import androidx.room.Transaction

interface UserWithContributionsDao {
    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithContributions(): List<UserWithContributions>

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserWithContributions(id: Int): UserWithContributions

}