package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.groupwithbills

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupWithBillsDao {
    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    suspend fun getGroupWithBills(groupId: Int): GroupWithBills

    @Transaction
    @Query("SELECT * FROM groups WHERE id = :groupId")
    fun getGroupWithBillsFlow(groupId: Int): Flow<GroupWithBills>
}