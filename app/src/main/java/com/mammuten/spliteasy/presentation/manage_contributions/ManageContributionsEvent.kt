package com.mammuten.spliteasy.presentation.manage_contributions

sealed interface ManageContributionsEvent {
    data class EnteredAmountPaid(val memberId: Int, val value: String) : ManageContributionsEvent
    data class EnteredAmountOwed(val memberId: Int, val value: String) : ManageContributionsEvent
    data object SaveContributions : ManageContributionsEvent
}