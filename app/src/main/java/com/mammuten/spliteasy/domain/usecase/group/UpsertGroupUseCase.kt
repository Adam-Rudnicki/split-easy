package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.InvalidGroupException

class UpsertGroupUseCase(
    private val groupRepo: GroupRepo
) {
    suspend operator fun invoke(group: Group) {
        groupRepo.upsertGroup(group)
    }
}