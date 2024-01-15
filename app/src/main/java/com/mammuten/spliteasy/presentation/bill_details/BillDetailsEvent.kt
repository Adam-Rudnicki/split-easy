package com.mammuten.spliteasy.presentation.bill_details

sealed interface BillDetailsEvent {
    data object DeleteBill : BillDetailsEvent
    data object DeleteContribution : BillDetailsEvent
}