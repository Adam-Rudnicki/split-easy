package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.repository.BillRepository
import com.mammuten.spliteasy.util.common.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val appDispatchers: AppDispatchers,
    private val billMapper: BillMapper,
) : BillRepository {
    override suspend fun upsertBill(bill: Bill) {
        withContext(appDispatchers.io) {
            db.billDao().upsertBills(billMapper.toEntity(bill))
        }
    }

    override suspend fun deleteBill(bill: Bill) {
        withContext(appDispatchers.io) {
            db.billDao().deleteBills(billMapper.toEntity(bill))
        }
    }

    override suspend fun getBillsByGroupId(groupId: Int): List<Bill> {
        return withContext(appDispatchers.io) {
            val bills = db.billDao().loadBillsByGroupId(groupId)
            billMapper.toDomainList(bills)
        }
    }

    override fun getBillsByGroupIdAsFlow(groupId: Int): Flow<List<Bill>> {
        return db.billDao().loadBillsByGroupIdAsFlow(groupId)
            .map { billMapper.toDomainList(it) }
            .flowOn(appDispatchers.io)
    }

}