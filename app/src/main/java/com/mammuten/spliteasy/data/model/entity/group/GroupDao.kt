package com.mammuten.spliteasy.data.model.entity.group

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GroupDao {
    @Upsert
    suspend fun upsertGroup(groupEntity: GroupEntity)

    @Delete
    suspend fun deleteGroup(groupEntity: GroupEntity)

    @Query("SELECT * FROM groups WHERE id = :id")
    suspend fun loadGroupById(id: Int): GroupEntity

//    @Query("SELECT * FROM groups WHERE id = :id")
//    fun loadGroupById(id: Int): Flow<Group>
}