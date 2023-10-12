package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.dto.Bill
import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.domain.repository.BillRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class BillRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val billMapper: BillMapper,
    private val contributionMapper: ContributionMapper
) : BillRepository {
    override suspend fun upsertBill(bill: Bill) {
        val billEntity = billMapper.toEntity(bill)
        db.billDao().upsertBill(billEntity)
    }

    override suspend fun deleteBill(bill: Bill) {
        db.billDao().deleteBill(billMapper.toEntity(bill))
    }

    override suspend fun getBillWithContributions(billId: Int): Resource<List<Contribution>> {
        val billWithContributions = db.billWithContributionsDao().getBillWithContributions(billId)
        return Resource.Success(billWithContributions.contributions.map {
            contributionMapper.toDomain(
                it
            )
        })
    }

    override fun getBillWithContributionsFlow(billId: Int): Flow<Resource<List<Contribution>>> {
        return flow {
            db.billWithContributionsDao().getBillWithContributionsFlow(billId)
                .collect { billWithContributions ->
                    emit(Resource.Loading())
                    emit(Resource.Success(billWithContributions.contributions.map {
                        contributionMapper.toDomain(
                            it
                        )
                    }))
                }
        }
    }

}