package com.mammuten.spliteasy.domain.usecase.contribution

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Contribution
import kotlinx.coroutines.flow.Flow

class GetContributionsByBillIdUseCase(
    private val contributionRepo: ContributionRepo
) {
    operator fun invoke(billId: Int): Flow<List<Contribution>> {
        return contributionRepo.getContributionsByBillId(billId)
    }
}