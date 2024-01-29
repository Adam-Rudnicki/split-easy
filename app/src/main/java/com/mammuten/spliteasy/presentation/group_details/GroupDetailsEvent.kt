package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.util.order.BillOrder

sealed interface GroupDetailsEvent {
    data object DeleteGroup : GroupDetailsEvent
    data class BillsOrder(val billOrder: BillOrder) : GroupDetailsEvent
    data object NavigateToAddEditGroupScreen : GroupDetailsEvent
    data object NavigateToAddEditBillScreen : GroupDetailsEvent
    data class NavigateToBillDetailsScreen(val billId: Int) : GroupDetailsEvent
}
