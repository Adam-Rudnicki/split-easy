package com.mammuten.spliteasy.data.source.local.model.entity.bill

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BillDao {
    @Upsert
    suspend fun upsertBill(billEntity: BillEntity)

    @Delete
    suspend fun deleteBill(billEntity: BillEntity)

}