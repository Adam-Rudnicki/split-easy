package com.mammuten.spliteasy.domain.usecase.user

import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUsersNotInGroupUseCase(
    private val userRepo: UserRepo
) {
    operator fun invoke(groupId: Int): Flow<List<User>> {
        return userRepo.getUsersNotInGroup(groupId)
    }
}
