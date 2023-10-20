package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
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
    private val contributionMapper: ContributionMapper
) : BillRepository {
    override suspend fun upsertBill(bill: Bill) {
        withContext(appDispatchers.io) {
            db.billDao().upsertBill(billMapper.toEntity(bill))
        }
    }

    override suspend fun deleteBill(bill: Bill) {
        withContext(appDispatchers.io) {
            db.billDao().deleteBill(billMapper.toEntity(bill))
        }
    }

    override suspend fun getBillWithContributions(billId: Int): List<Contribution> {
        return withContext(appDispatchers.io) {
            val billWithContributions =
                db.billWithContributionsDao().getBillWithContributions(billId)
            contributionMapper.toDomainList(billWithContributions.contributions)
        }
    }

    override fun getBillWithContributionsFlow(billId: Int): Flow<List<Contribution>> {
        return db.billWithContributionsDao().getBillWithContributionsFlow(billId)
            .map { billWithContributions ->
                contributionMapper.toDomainList(billWithContributions.contributions)
            }.flowOn(appDispatchers.io)
    }

}