package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.dto.Bill
import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

interface BillRepository {
    suspend fun upsertBill(bill: Bill)

    suspend fun deleteBill(bill: Bill)

    suspend fun getBillWithContributions(billId: Int): Resource<List<Contribution>>

    fun getBillWithContributionsFlow(billId: Int): Flow<Resource<List<Contribution>>>

}