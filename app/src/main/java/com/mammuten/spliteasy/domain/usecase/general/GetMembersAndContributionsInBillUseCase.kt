package com.mammuten.spliteasy.domain.usecase.general

import com.mammuten.spliteasy.data.repo.GeneralRepo
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.domain.util.order.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMembersAndContributionsInBillUseCase(
    private val generalRepo: GeneralRepo
) {
    operator fun invoke(
        billId: Int,
        contributionOrder: ContributionOrder = ContributionOrder.AmountPaid(OrderType.Ascending)
    ): Flow<List<Pair<Member, Contribution>>> {
        return generalRepo.getMembersAndContributionsInBill(billId).map { map ->
            val list = map.toList()
            when (contributionOrder) {
                is ContributionOrder.AmountPaid -> {
                    when (contributionOrder.orderType) {
                        is OrderType.Ascending -> list.sortedBy { (_, value) -> value.amountPaid }
                        is OrderType.Descending -> list.sortedByDescending { (_, value) -> value.amountOwed }
                    }
                }

                is ContributionOrder.AmountOwed -> {
                    when (contributionOrder.orderType) {
                        is OrderType.Ascending -> list.sortedBy { (_, value) -> value.amountPaid }
                        is OrderType.Descending -> list.sortedByDescending { (_, value) -> value.amountOwed }
                    }
                }
            }
        }
    }
}