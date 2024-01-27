package com.mammuten.spliteasy.presentation.group_members

import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.MemberOrder

data class GroupMembersState(
    val members: List<Member> = emptyList(),
    val memberOrder: MemberOrder = MemberOrder.NameAscending,
)