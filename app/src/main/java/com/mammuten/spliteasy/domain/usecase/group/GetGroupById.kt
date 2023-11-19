package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.repository.GroupRepository
import javax.inject.Inject

class GetGroupById @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(groupId: Int): Group {
        return groupRepository.getGroupById(groupId)
    }
}