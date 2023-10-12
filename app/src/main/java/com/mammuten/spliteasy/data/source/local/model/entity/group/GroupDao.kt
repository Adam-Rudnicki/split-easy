package com.mammuten.spliteasy.data.source.local.model.entity.group

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Upsert
    suspend fun upsertGroup(groupEntity: GroupEntity)

    @Delete
    suspend fun deleteGroup(groupEntity: GroupEntity)

    @Query("SELECT * FROM groups")
    suspend fun getAllGroups(): List<GroupEntity>

    @Query("SELECT * FROM groups")
    fun getAllGroupsFlow(): Flow<List<GroupEntity>>
}