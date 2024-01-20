package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.BillOrder
import com.mammuten.spliteasy.domain.util.order.MemberOrder

sealed interface GroupDetailsEvent {
    data object DeleteGroup : GroupDetailsEvent
    data class DeleteMember(val member: Member) : GroupDetailsEvent
    data object RestoreMember : GroupDetailsEvent
    data class BillsOrder(val billOrder: BillOrder) : GroupDetailsEvent
    data class MembersOrder(val memberOrder: MemberOrder) : GroupDetailsEvent
}
