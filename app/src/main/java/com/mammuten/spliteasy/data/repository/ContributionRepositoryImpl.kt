package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.repository.ContributionRepository
import com.mammuten.spliteasy.util.common.AppDispatchers
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
            db.contributionDao().upsertContribution(contributionMapper.toEntity(contribution))
        }
    }

    override suspend fun deleteContribution(contribution: Contribution) {
        withContext(appDispatchers.io) {
            db.contributionDao().deleteContribution(contributionMapper.toEntity(contribution))
        }
    }
}