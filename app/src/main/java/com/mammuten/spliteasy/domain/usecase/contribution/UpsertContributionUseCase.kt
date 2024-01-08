package com.mammuten.spliteasy.domain.usecase.contribution

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.util.ContributionExists
import kotlinx.coroutines.flow.firstOrNull

class UpsertContributionUseCase(
    private val contributionRepo: ContributionRepo
) {
    suspend operator fun invoke(contribution: Contribution) {
        val existingContribution = contributionRepo.getContributionByBillIdAndMemberId(
            contribution.billId,
            contribution.memberId
        ).firstOrNull()

        existingContribution?.let {
            throw ContributionExists("Contribution already exists")
        } ?: contributionRepo.upsertContribution(contribution)
    }
}