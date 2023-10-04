package com.mammuten.spliteasy.data.model.associativeentity.billusercrossref

import androidx.room.Query
import androidx.room.Transaction

interface BillUserCrossRefDao {
    @Transaction
    @Query("SELECT * FROM bill_user_cross_ref")
    suspend fun getBillsWithUsers(): List<BillUserCrossRef>
}