package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.repository.GroupRepository
import javax.inject.Inject

class DeleteGroup @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(group: Group) {
        groupRepository.deleteGroup(group)
    }
}