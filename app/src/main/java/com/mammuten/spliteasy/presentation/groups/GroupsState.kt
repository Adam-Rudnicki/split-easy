package com.mammuten.spliteasy.presentation.groups

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.order.GroupOrder

data class GroupsState(
    val groups: List<Group> = emptyList(),
    val groupOrder: GroupOrder = GroupOrder.DateDesc,
)