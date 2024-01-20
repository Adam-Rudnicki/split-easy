package com.mammuten.spliteasy.domain.usecase.user

import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.util.order.OrderType
import com.mammuten.spliteasy.domain.util.order.UserOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(
    private val userRepo: UserRepo
) {
    operator fun invoke(
        userOrder: UserOrder = UserOrder.Name(OrderType.Descending)
    ): Flow<List<User>> {
        return userRepo.getUsers().map { users ->
            when (userOrder) {
                is UserOrder.Name -> {
                    when (userOrder.orderType) {
                        is OrderType.Ascending -> users.sortedBy { it.name.lowercase() }
                        is OrderType.Descending -> users.sortedByDescending { it.name.lowercase() }
                    }
                }

                is UserOrder.Surname -> {
                    when (userOrder.orderType) {
                        is OrderType.Ascending -> users.sortedWith(compareBy(nullsLast()) { it.surname })
                        is OrderType.Descending -> users.sortedWith(compareByDescending(nullsFirst()) { it.surname })
                    }
                }

                is UserOrder.Nick -> {
                    when (userOrder.orderType) {
                        is OrderType.Ascending -> users.sortedWith(compareBy(nullsLast()) { it.nick })
                        is OrderType.Descending -> users.sortedWith(compareByDescending(nullsFirst()) { it.nick })
                    }
                }
            }
        }
    }
}