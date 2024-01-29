package com.mammuten.spliteasy.domain.usecase.contribution

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetContributionsByBillIdUseCase(
    private val contributionRepo: ContributionRepo
) {
    operator fun invoke(
        billId: Int,
        contributionOrder: ContributionOrder? = null
    ): Flow<List<Contribution>> {
        return contributionRepo.getContributionsByBillId(billId).map { contributions ->
            contributionOrder?.let { order ->
                when (order) {
                    is ContributionOrder.AmountPaidAsc -> contributions.sortedBy { it.amountPaid }
                    is ContributionOrder.AmountPaidDesc -> contributions.sortedByDescending { it.amountPaid }

                    is ContributionOrder.AmountOwedAsc -> contributions.sortedBy { it.amountOwed }
                    is ContributionOrder.AmountOwedDesc -> contributions.sortedByDescending { it.amountOwed }
                }
            } ?: contributions
        }
    }
}
