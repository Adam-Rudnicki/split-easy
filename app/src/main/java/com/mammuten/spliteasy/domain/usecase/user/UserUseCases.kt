package com.mammuten.spliteasy.domain.usecase.user

data class UserUseCases(
    val upsertUserUseCase: UpsertUserUseCase,
    val deleteUserUseCase: DeleteUserUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersUseCase: GetUsersUseCase,
    val getUsersNotInGroupUseCase: GetUsersNotInGroupUseCase
)