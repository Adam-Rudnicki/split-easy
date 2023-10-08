package com.mammuten.spliteasy.data.model.entity.associativeentity.groupusercrossref

import androidx.room.Query
import androidx.room.Transaction

interface GroupUserCrossRefDao {
    @Transaction
    @Query("SELECT * FROM group_user_cross_ref")
    suspend fun getGroupsWithUsers(): List<GroupUserCrossRefEntity>
}