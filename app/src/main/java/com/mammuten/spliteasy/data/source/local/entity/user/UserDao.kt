package com.mammuten.spliteasy.data.source.local.entity.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(userEntity: UserEntity)

    @Delete
    suspend fun deleteUser(userEntity: UserEntity)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE id = :userId")
    fun getUserById(userId: Int): Flow<UserEntity?>

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * FROM users WHERE id NOT IN (SELECT userId FROM members WHERE groupId = :groupId AND userId IS NOT NULL)")
    fun getUsersNotInGroup(groupId: Int): Flow<List<UserEntity>>
}