package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.domain.model.Group

class UpsertGroupUseCase(
    private val groupRepo: GroupRepo
) {
    suspend operator fun invoke(group: Group) {
        groupRepo.upsertGroup(group)
    }
}