package com.mammuten.spliteasy.domain.usecase.contribution

data class ContributionUseCases(
    val upsertContributionUseCase: UpsertContributionUseCase,
    val deleteContributionUseCase: DeleteContributionUseCase,
    val updateContributionsUseCase: UpdateContributionsUseCase,
    val getContributionsByBillIdUseCase: GetContributionsByBillIdUseCase,
    val getContributionByBillIdAndMemberIdUseCase: GetContributionByBillIdAndMemberIdUseCase
)