package com.mammuten.spliteasy.data.source.local.model.entity.group

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Upsert
    suspend fun upsertGroups(vararg groups: GroupEntity)

    @Delete
    suspend fun deleteGroups(vararg groups: GroupEntity)

    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun loadGroupById(groupId: Int): GroupEntity

    @Query("SELECT * FROM groups")
    suspend fun loadGroups(): List<GroupEntity>

    @Query("SELECT * FROM groups")
    fun loadGroupsAsFlow(): Flow<List<GroupEntity>>

}