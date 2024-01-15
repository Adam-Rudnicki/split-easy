package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.BillOrder

sealed interface GroupDetailsEvent {
    data object DeleteGroup : GroupDetailsEvent
    data class DeleteMember(val member: Member) : GroupDetailsEvent
    data object RestoreMember : GroupDetailsEvent
    data class Order(val billOrder: BillOrder) : GroupDetailsEvent
}
