package com.mammuten.spliteasy.data.model.relation.manytomany.userwithgroups

import androidx.room.Query
import androidx.room.Transaction

interface UserWithGroupsDao {
    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithGroups(): List<UserWithGroups>
}