package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.repository.ContributionRepository
import com.mammuten.spliteasy.util.common.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContributionRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val appDispatchers: AppDispatchers,
    private val contributionMapper: ContributionMapper
) : ContributionRepository {
    override suspend fun upsertContribution(contribution: Contribution) {
        withContext(appDispatchers.io) {
            db.contributionDao().upsertContributions(contributionMapper.toEntity(contribution))
        }
    }

    override suspend fun deleteContribution(contribution: Contribution) {
        withContext(appDispatchers.io) {
            db.contributionDao().deleteContribution(contributionMapper.toEntity(contribution))
        }
    }

    override suspend fun getContributionsByBillId(billId: Int): List<Contribution> {
        return withContext(appDispatchers.io) {
            val contributions = db.contributionDao().loadContributionsByBillId(billId)
            contributionMapper.toDomainList(contributions)
        }
    }

    override fun getContributionsByBillIdAsFlow(billId: Int): Flow<List<Contribution>> {
        return db.contributionDao().loadContributionsByBillIdAsFlow(billId)
            .map { contributionMapper.toDomainList(it) }
            .flowOn(appDispatchers.io)
    }

    override suspend fun getContributionsByMemberId(memberId: Int): List<Contribution> {
        return withContext(appDispatchers.io) {
            val contributions = db.contributionDao().loadContributionsByMemberId(memberId)
            contributionMapper.toDomainList(contributions)
        }
    }

    override fun getContributionsByMemberIdAsFlow(memberId: Int): Flow<List<Contribution>> {
        return db.contributionDao().loadContributionsByMemberIdAsFlow(memberId)
            .map { contributionMapper.toDomainList(it) }
            .flowOn(appDispatchers.io)
    }
}