package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.domain.repository.ContributionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContributionRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val contributionMapper: ContributionMapper
) : ContributionRepository {
    override suspend fun upsertContribution(contribution: Contribution) {
        val contributionEntity = contributionMapper.toEntity(contribution)
        db.contributionDao().upsertContribution(contributionEntity)
    }

    override suspend fun deleteContribution(contribution: Contribution) {
        db.contributionDao().deleteContribution(contributionMapper.toEntity(contribution))
    }
}