package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithmembers

import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

interface GroupWithMembersDao {
    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupWithMembers(groupId: Int): GroupWithMembers

    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    fun getGroupWithMembersFlow(groupId: Int): Flow<GroupWithMembers>
}