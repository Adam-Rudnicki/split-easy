package com.mammuten.spliteasy.domain.usecase.group

data class GroupUseCases (
    val upsertGroupUseCase: UpsertGroupUseCase,
    val deleteGroupUseCase: DeleteGroupUseCase,
    val getGroupByIdUseCase: GetGroupByIdUseCase,
    val getGroups: GetGroupsUseCase
)