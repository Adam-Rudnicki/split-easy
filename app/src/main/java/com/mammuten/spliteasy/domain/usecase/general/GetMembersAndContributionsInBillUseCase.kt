package com.mammuten.spliteasy.domain.usecase.general

import com.mammuten.spliteasy.data.repo.GeneralRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMembersAndContributionsInBillUseCase(
    private val generalRepo: GeneralRepo
) {
    operator fun invoke(
        billId: Int,
        contributionOrder: ContributionOrder? = null
    ): Flow<Map<Member, Contribution>> {
        return generalRepo.getMembersAndContributionsInBill(billId).map { map ->
            contributionOrder?.let { order ->
                map.toList().let { list ->
                    when (order) {
                        is ContributionOrder.AmountPaidAsc -> list.sortedBy { it.second.amountPaid }
                        is ContributionOrder.AmountPaidDesc -> list.sortedByDescending { it.second.amountPaid }
                        is ContributionOrder.AmountOwedAsc -> list.sortedBy { it.second.amountOwed }
                        is ContributionOrder.AmountOwedDesc -> list.sortedByDescending { it.second.amountOwed }
                    }
                }.toMap()
            } ?: map
        }
    }
}