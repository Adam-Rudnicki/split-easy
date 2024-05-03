package com.mammuten.spliteasy.data.source.local.entity.bill

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Upsert
    suspend fun upsertBill(billEntity: BillEntity)

    @Delete
    suspend fun deleteBill(billEntity: BillEntity)

    @Query("SELECT * FROM ${BillEntity.TABLE_NAME} WHERE id = :billId")
    fun getBillById(billId: Int): Flow<BillEntity?>

    @Query("SELECT * FROM ${BillEntity.TABLE_NAME} WHERE groupId = :groupId")
    fun getBillsByGroupId(groupId: Int): Flow<List<BillEntity>>
}