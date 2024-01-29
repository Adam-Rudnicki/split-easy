package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.order.GroupOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGroupsUseCase(
    private val groupRepo: GroupRepo
) {
    operator fun invoke(
        groupOrder: GroupOrder = GroupOrder.DateDesc
    ): Flow<List<Group>> {
        return groupRepo.getGroups().map { groups ->
            when (groupOrder) {
                is GroupOrder.NameAsc -> groups.sortedBy { it.name.lowercase() }
                is GroupOrder.NameDesc -> groups.sortedByDescending { it.name.lowercase() }

                is GroupOrder.DateAsc -> groups.sortedBy { it.created }
                is GroupOrder.DateDesc -> groups.sortedByDescending { it.created }
            }
        }
    }
}

