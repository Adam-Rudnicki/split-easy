package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    suspend fun upsertBill(bill: Bill)

    suspend fun deleteBill(bill: Bill)

    suspend fun getBillWithContributions(billId: Int): List<Contribution>

    fun getBillWithContributionsFlow(billId: Int): Flow<List<Contribution>>

}