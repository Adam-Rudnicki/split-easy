package com.mammuten.spliteasy.presentation.groups

import com.mammuten.spliteasy.domain.util.order.GroupOrder

sealed interface GroupsEvent {
    data class Order(val groupOrder: GroupOrder) : GroupsEvent
}