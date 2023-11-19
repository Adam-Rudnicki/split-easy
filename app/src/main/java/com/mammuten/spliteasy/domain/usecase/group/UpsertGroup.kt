package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.InvalidGroupException
import javax.inject.Inject

class UpsertGroup @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(group: Group) {
        if (group.name.isBlank()) throw InvalidGroupException("Group name can't be empty.")
        groupRepository.upsertGroup(group)
    }
}