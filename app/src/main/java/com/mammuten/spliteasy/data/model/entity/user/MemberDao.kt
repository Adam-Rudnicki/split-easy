package com.mammuten.spliteasy.data.model.entity.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MemberDao {
    @Upsert
    suspend fun upsertUser(memberEntity: MemberEntity)

    @Delete
    suspend fun deleteUser(memberEntity: MemberEntity)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun loadUserById(id: Int): MemberEntity

//    @Query("SELECT * FROM users WHERE id = :id")
//    fun loadUserById(id: Int): Flow<User>
}