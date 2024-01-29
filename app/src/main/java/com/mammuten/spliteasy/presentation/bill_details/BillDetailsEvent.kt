package com.mammuten.spliteasy.presentation.bill_details

import com.mammuten.spliteasy.domain.model.Contribution
import com.mammuten.spliteasy.domain.util.order.ContributionOrder

sealed interface BillDetailsEvent {
    data object DeleteBill : BillDetailsEvent
    data class DeleteContribution(val contribution: Contribution) : BillDetailsEvent
    data object RestoreContribution : BillDetailsEvent
    data class ContributionsOrder(val contributionOrder: ContributionOrder) : BillDetailsEvent
    data object NavigateToAddEditBillScreen : BillDetailsEvent
    data object NavigateToManageContributionsScreen : BillDetailsEvent
}