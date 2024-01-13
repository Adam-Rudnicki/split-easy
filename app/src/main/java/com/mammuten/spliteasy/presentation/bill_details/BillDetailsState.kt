package com.mammuten.spliteasy.presentation.bill_details

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.ContributionOrder

data class BillDetailsState(
    val bill: Bill? = null,
    val contributions: List<Contribution> = emptyList(),
    val contributionOrder: ContributionOrder? = null,
    val membersWithContributions: List<Member> = emptyList(),
    val membersWithoutContributions: List<Member> = emptyList()
)