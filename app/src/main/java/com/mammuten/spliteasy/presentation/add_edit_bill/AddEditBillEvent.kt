package com.mammuten.spliteasy.presentation.add_edit_bill

import java.util.Date

sealed interface AddEditBillEvent {
    data class EnteredName(val value: String) : AddEditBillEvent
    data class EnteredDescription(val value: String) : AddEditBillEvent
    data class EnteredAmount(val value: String) : AddEditBillEvent
    data class EnteredDate(val value: Date?) : AddEditBillEvent
    data object SaveBill : AddEditBillEvent
}
