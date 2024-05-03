package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asEntity
import com.mammuten.spliteasy.data.mapper.asModel
import com.mammuten.spliteasy.data.mapper.asBillModelList
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.Bill
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BillRepo(
    private val dataSource: LocalSplitDataSource
) {
    suspend fun upsertBill(bill: Bill) = dataSource.upsertBill(bill.asEntity())

    suspend fun deleteBill(bill: Bill) = dataSource.deleteBill(bill.asEntity())

    fun getBillById(billId: Int): Flow<Bill?> =
        dataSource.getBillById(billId).map { it?.asModel() }

    fun getBillsByGroupId(groupId: Int): Flow<List<Bill>> =
        dataSource.getBillsByGroupId(groupId).map { it.asBillModelList() }
}