package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asContributionEntityList
import com.mammuten.spliteasy.data.mapper.asContributionModelList
import com.mammuten.spliteasy.data.mapper.asEntity
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.Contribution
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContributionRepo(
    private val dataSource: LocalSplitDataSource
) {
    suspend fun upsertContribution(contribution: Contribution) =
        dataSource.upsertContribution(contribution.asEntity())

    suspend fun deleteContribution(contribution: Contribution) =
        dataSource.deleteContribution(contribution.asEntity())

    suspend fun updateContributions(
        contributionEntitiesToUpsert: List<Contribution>,
        contributionEntitiesToDelete: List<Contribution>
    ) = dataSource.updateContributions(
        contributionEntitiesToUpsert.asContributionEntityList(),
        contributionEntitiesToDelete.asContributionEntityList()
    )

    fun getContributionsByBillId(billId: Int): Flow<List<Contribution>> =
        dataSource.getContributionsByBillId(billId).map { it.asContributionModelList() }
}
