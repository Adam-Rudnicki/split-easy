package com.mammuten.spliteasy.domain.usecase.user

import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(
    private val userRepo: UserRepo
) {
    operator fun invoke(userId: Int): Flow<User?> {
        return userRepo.getUserById(userId)
    }
}
