package com.mammuten.spliteasy.data.source.local.model.entity.bill

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Upsert
    suspend fun upsertBills(vararg bills: BillEntity)

    @Delete
    suspend fun deleteBills(vararg bills: BillEntity)

    @Query("SELECT * FROM bills WHERE groupId = :groupId")
    suspend fun loadBillsByGroupId(groupId: Int): List<BillEntity>

    @Query("SELECT * FROM bills WHERE groupId = :groupId")
    fun loadBillsByGroupIdAsFlow(groupId: Int): Flow<List<BillEntity>>

}