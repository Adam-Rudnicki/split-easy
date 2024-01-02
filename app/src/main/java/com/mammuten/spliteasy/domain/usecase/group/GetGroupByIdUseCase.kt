package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.domain.model.Group
import kotlinx.coroutines.flow.Flow

class GetGroupByIdUseCase(
    private val groupRep: GroupRepo
) {
    operator fun invoke(groupId: Int): Flow<Group?> {
        return groupRep.getGroupById(groupId)
    }
}