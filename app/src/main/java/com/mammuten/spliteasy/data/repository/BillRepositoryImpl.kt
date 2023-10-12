package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.dto.Bill
import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.domain.repository.BillRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BillRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val billMapper: BillMapper
) : BillRepository {
    override suspend fun upsertBill(bill: Bill) {
        val billEntity = billMapper.toEntity(bill)
        db.billDao().upsertBill(billEntity)
    }

    override suspend fun deleteBill(bill: Bill) {
        db.billDao().deleteBill(billMapper.toEntity(bill))
    }

    override suspend fun getBillWithContributions(billId: Int): Resource<List<Contribution>> {
        TODO("Not yet implemented")
    }

    override fun getBillWithContributionsFlow(billId: Int): Flow<Resource<List<Contribution>>> {
        TODO("Not yet implemented")
    }

}