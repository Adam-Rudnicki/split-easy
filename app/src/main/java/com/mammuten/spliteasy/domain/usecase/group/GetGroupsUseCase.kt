package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.data.repo.GroupRepo
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGroupsUseCase(
    private val groupRepository: GroupRepo
) {
    operator fun invoke(
        groupOrder: GroupOrder = GroupOrder.Date(OrderType.Descending)
    ): Flow<List<Group>> {
        return groupRepository.getGroups().map { groups ->
            when (groupOrder) {
                is GroupOrder.Name -> {
                    when (groupOrder.orderType) {
                        is OrderType.Ascending -> groups.sortedBy { it.name.lowercase() }
                        is OrderType.Descending -> groups.sortedByDescending { it.name.lowercase() }
                    }
                }

                is GroupOrder.Date -> {
                    when (groupOrder.orderType) {
                        is OrderType.Ascending -> groups.sortedBy { it.created }
                        is OrderType.Descending -> groups.sortedByDescending { it.created }
                    }
                }
            }
        }
    }
}
