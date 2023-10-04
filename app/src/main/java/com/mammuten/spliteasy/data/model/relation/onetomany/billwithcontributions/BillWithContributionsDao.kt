package com.mammuten.spliteasy.data.model.relation.onetomany.billwithcontributions

import androidx.room.Query
import androidx.room.Transaction

interface BillWithContributionsDao {
    @Transaction
    @Query("SELECT * FROM bills")
    suspend fun getBillsWithContributions(): List<BillWithContributions>
}