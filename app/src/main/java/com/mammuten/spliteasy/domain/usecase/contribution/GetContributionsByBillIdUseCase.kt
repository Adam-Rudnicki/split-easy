package com.mammuten.spliteasy.domain.usecase.contribution

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.domain.util.order.OrderType
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
                    is ContributionOrder.AmountPaid -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> contributions.sortedBy { it.amountPaid }
                            is OrderType.Descending -> contributions.sortedByDescending { it.amountPaid }
                        }
                    }

                    is ContributionOrder.AmountOwed -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> contributions.sortedBy { it.amountOwed }
                            is OrderType.Descending -> contributions.sortedByDescending { it.amountOwed }
                        }
                    }
                }
            } ?: contributions
        }
    }
}
