package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.domain.util.order.MemberOrder
import com.mammuten.spliteasy.domain.util.order.OrderType

data class GroupDetailsState(
    val group: Group? = null,
    val bills: List<Bill> = emptyList(),
    val billOrder: BillOrder = BillOrder.Date(OrderType.Descending),
    val members: List<Member> = emptyList(),
    val memberOrder: MemberOrder = MemberOrder.Name(OrderType.Ascending),
)