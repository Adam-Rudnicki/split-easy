package com.mammuten.spliteasy.presentation.users

import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.util.order.OrderType
import com.mammuten.spliteasy.domain.util.order.UserOrder

data class UsersState(
    val users: List<User> = emptyList(),
    val userOrder: UserOrder = UserOrder.Name(OrderType.Ascending),
)