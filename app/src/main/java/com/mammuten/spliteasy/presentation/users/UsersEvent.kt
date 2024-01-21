package com.mammuten.spliteasy.presentation.users

import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.util.order.UserOrder

sealed interface UsersEvent {
    data class DeleteUser(val user: User) : UsersEvent
    data class Order(val userOrder: UserOrder) : UsersEvent
}