package com.mammuten.spliteasy.data.model.relation.onetomany.groupwithmembers

import androidx.room.Query
import androidx.room.Transaction

interface GroupWithMembersDao {
    @Transaction
    @Query("SELECT * FROM groups")
    suspend fun getGroupsWithMembers(): List<GroupWithMembers>

    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupWithMembers(groupId: Int): GroupWithMembers
}