package com.mammuten.spliteasy.data.model.relation.manytomany.groupwithusers

import androidx.room.Query
import androidx.room.Transaction

interface GroupWithUsersDao {
    @Transaction
    @Query("SELECT * FROM groups")
    fun getGroupsWithUsers(): List<GroupWithUsers>
}