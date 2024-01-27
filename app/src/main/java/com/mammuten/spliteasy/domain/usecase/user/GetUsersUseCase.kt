package com.mammuten.spliteasy.domain.usecase.user

import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.domain.model.User
import com.mammuten.spliteasy.domain.util.order.UserOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUsersUseCase(
    private val userRepo: UserRepo
) {
    operator fun invoke(
        userOrder: UserOrder = UserOrder.NameAsc
    ): Flow<List<User>> {
        return userRepo.getUsers().map { users ->
            when (userOrder) {
                is UserOrder.NameAsc -> users.sortedBy { it.name.lowercase() }
                is UserOrder.NameDesc -> users.sortedByDescending { it.name.lowercase() }
                is UserOrder.SurnameAsc -> users.sortedWith(compareBy(nullsLast()) { it.surname })
                is UserOrder.SurnameDesc -> users.sortedWith(compareByDescending(nullsFirst()) { it.surname })
                is UserOrder.NickAsc -> users.sortedWith(compareBy(nullsLast()) { it.nick })
                is UserOrder.NickDesc -> users.sortedWith(compareByDescending(nullsFirst()) { it.nick })
            }
        }
    }
}