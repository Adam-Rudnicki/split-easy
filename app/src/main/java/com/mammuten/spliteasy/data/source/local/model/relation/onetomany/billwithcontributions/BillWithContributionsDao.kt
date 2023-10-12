package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.billwithcontributions

import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

interface BillWithContributionsDao {
    @Transaction
    @Query("SELECT * FROM bills WHERE id = :billId")
    suspend fun getBillWithContributions(billId: Int): BillWithContributions

    @Transaction
    @Query("SELECT * FROM bills WHERE id = :billId")
    fun getBillWithContributionsFlow(billId: Int): Flow<BillWithContributions>

}