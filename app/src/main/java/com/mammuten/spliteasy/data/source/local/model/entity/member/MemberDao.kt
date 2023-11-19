package com.mammuten.spliteasy.data.source.local.model.entity.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Upsert
    suspend fun upsertMembers(vararg members: MemberEntity)

    @Delete
    suspend fun deleteMembers(vararg members: MemberEntity)

    @Query("SELECT * FROM members WHERE groupId = :groupId")
    suspend fun loadMembersByGroupId(groupId: Int): List<MemberEntity>

    @Query("SELECT * FROM members WHERE groupId = :groupId")
    fun loadMembersByGroupIdAsFlow(groupId: Int): Flow<List<MemberEntity>>

}