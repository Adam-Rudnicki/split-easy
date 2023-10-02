package com.mammuten.spliteasy.data.model.entity.group

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Upsert
    suspend fun upsertGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Query("SELECT * FROM groups WHERE id = :id")
    suspend fun loadGroupById(id: Int): Group

//    @Query("SELECT * FROM groups WHERE id = :id")
//    fun loadGroupById(id: Int): Flow<Group>
}