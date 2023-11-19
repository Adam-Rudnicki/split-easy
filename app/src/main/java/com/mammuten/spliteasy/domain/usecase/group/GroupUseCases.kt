package com.mammuten.spliteasy.domain.usecase.group

data class GroupUseCases(
    val upsertGroup: UpsertGroup,
    val deleteGroup: DeleteGroup,
    val getGroupById: GetGroupById,
    val getGroups: GetGroups,
    val getGroupsAsFlow: GetGroupsAsFlow
)
