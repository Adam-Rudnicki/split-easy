package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Bill
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    suspend fun upsertBill(bill: Bill)

    suspend fun deleteBill(bill: Bill)

    suspend fun getBillsByGroupId(groupId: Int): List<Bill>

    fun getBillsByGroupIdAsFlow(groupId: Int): Flow<List<Bill>>

}