package com.mammuten.spliteasy.presentation.group_members

import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.MemberOrder

sealed interface GroupMembersEvent {
    data class DeleteMember(val member: Member) : GroupMembersEvent
    data object RestoreMember : GroupMembersEvent
    data class MembersOrder(val memberOrder: MemberOrder) : GroupMembersEvent
    data object NavigateToAddMemberScreen : GroupMembersEvent
    data object NavigateToAddUsersScreen : GroupMembersEvent
}