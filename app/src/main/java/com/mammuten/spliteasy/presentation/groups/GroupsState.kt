package com.mammuten.spliteasy.presentation.groups

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType

data class GroupsState(
    val groups: List<Group> = emptyList(),
    val groupOrder: GroupOrder = GroupOrder.Date(OrderType.Descending),
)