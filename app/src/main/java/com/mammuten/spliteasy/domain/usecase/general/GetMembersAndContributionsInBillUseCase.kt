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
                when (order) {
                    is ContributionOrder.AmountPaidAsc -> map.toSortedMap(compareBy { map[it]!!.amountPaid })
                    is ContributionOrder.AmountPaidDesc -> map.toSortedMap(compareByDescending { map[it]!!.amountPaid })

                    is ContributionOrder.AmountOwedAsc -> map.toSortedMap(compareBy { map[it]!!.amountOwed })
                    is ContributionOrder.AmountOwedDesc -> map.toSortedMap(compareByDescending { map[it]!!.amountOwed })
                }
            } ?: map
        }
    }
}