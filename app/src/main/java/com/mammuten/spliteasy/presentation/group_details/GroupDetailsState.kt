package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.order.BillOrder

data class GroupDetailsState(
    val group: Group? = null,
    val bills: List<Bill> = emptyList(),
    val billOrder: BillOrder = BillOrder.DateDesc
)