package com.mammuten.spliteasy.data.model.relation.onetomany.groupwithbills

import androidx.room.Query
import androidx.room.Transaction

interface GroupWithBillsDao {
    @Transaction
    @Query("SELECT * FROM groups")
    suspend fun getGroupsWithBills(): List<GroupWithBills>
}