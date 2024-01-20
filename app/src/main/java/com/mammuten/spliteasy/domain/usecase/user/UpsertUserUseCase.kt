package com.mammuten.spliteasy.domain.usecase.user

import com.mammuten.spliteasy.data.repo.UserRepo
import com.mammuten.spliteasy.domain.model.User

class UpsertUserUseCase(
    private val userRepo: UserRepo
) {
    suspend operator fun invoke(user: User) {
        userRepo.upsertUser(user)
    }
}