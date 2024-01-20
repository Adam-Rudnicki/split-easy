package com.mammuten.spliteasy.presentation.bill_details

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.ContributionOrder
import com.mammuten.spliteasy.domain.util.order.OrderType

data class BillDetailsState(
    val bill: Bill? = null,
    val membersAndContributions: List<Pair<Member, Contribution>> = emptyList(),
    val contributionOrder: ContributionOrder = ContributionOrder.AmountPaid(OrderType.Descending),
)