package com.mammuten.spliteasy.data.model.entity.bill

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Upsert
    suspend fun upsertBill(bill: Bill)

    @Delete
    suspend fun deleteBill(bill: Bill)

    @Query("SELECT * FROM bills WHERE id = :id")
    suspend fun loadBillById(id: Int): Bill

//    @Query("SELECT * FROM bills WHERE id = :id")
//    fun loadBillById(id: Int): Flow<Bill>
}