package com.mammuten.spliteasy.domain.usecase.contribution

data class ContributionUseCases(
    val upsertContributionUseCase: UpsertContributionUseCase,
    val deleteContributionUseCase: DeleteContributionUseCase,
    val getContributionsByBillIdUseCase: GetContributionsByBillIdUseCase
)