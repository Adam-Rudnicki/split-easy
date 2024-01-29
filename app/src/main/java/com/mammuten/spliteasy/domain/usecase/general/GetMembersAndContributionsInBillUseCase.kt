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
        contributionOrder: ContributionOrder = ContributionOrder.AmountPaidAsc
    ): Flow<List<Pair<Member, Contribution>>> {
        return generalRepo.getMembersAndContributionsInBill(billId).map { map ->
            val list = map.toList()
            when (contributionOrder) {
                is ContributionOrder.AmountPaidAsc -> list.sortedBy { (_, value) -> value.amountPaid }
                is ContributionOrder.AmountPaidDesc -> list.sortedByDescending { (_, value) -> value.amountPaid }

                is ContributionOrder.AmountOwedAsc -> list.sortedBy { (_, value) -> value.amountOwed }
                is ContributionOrder.AmountOwedDesc -> list.sortedByDescending { (_, value) -> value.amountOwed }
            }
        }
    }
}