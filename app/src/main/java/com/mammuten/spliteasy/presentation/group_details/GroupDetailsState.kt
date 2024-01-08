package com.mammuten.spliteasy.presentation.group_details

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.BillOrder
import com.mammuten.spliteasy.domain.util.MemberOrder

data class GroupDetailsState(
    val group: Group? = null,
    val bills: List<Bill> = emptyList(),
    val billOrder: BillOrder? = null,
    val members: List<Member> = emptyList(),
    val memberOrder: MemberOrder? = null,
)