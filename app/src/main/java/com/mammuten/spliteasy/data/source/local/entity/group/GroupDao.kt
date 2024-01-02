package com.mammuten.spliteasy.data.source.local.entity.group

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

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE id = :groupId")
    fun getGroupById(groupId: Int): Flow<GroupEntity?>

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    fun getGroups(): Flow<List<GroupEntity>>
}