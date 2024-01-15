package com.mammuten.spliteasy.presentation.add_edit_contribution

sealed interface AddEditContributionEvent {
    data class EnteredMemberId(val value: Int) : AddEditContributionEvent
    data class EnteredAmountPaid(val value: String) : AddEditContributionEvent
    data class EnteredAmountOwed(val value: String) : AddEditContributionEvent
    data object SaveContribution : AddEditContributionEvent
}