package com.mammuten.spliteasy.domain.usecase.contribution

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Contribution

class DeleteContributionUseCase(
    private val contributionRepo: ContributionRepo
) {
    suspend operator fun invoke(contribution: Contribution) {
        contributionRepo.deleteContribution(contribution)
    }
}