package com.mammuten.spliteasy.data.model.entity.bill

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

    @Query("SELECT * FROM bills WHERE id = :id")
    suspend fun loadBillById(id: Int): BillEntity

//    @Query("SELECT * FROM bills WHERE id = :id")
//    fun loadBillById(id: Int): Flow<Bill>
}