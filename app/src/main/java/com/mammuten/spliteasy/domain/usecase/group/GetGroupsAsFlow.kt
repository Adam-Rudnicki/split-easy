package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGroupsAsFlow @Inject constructor(
    private val groupRepository: GroupRepository
) {
    operator fun invoke(
        groupOrder: GroupOrder = GroupOrder.Name(OrderType.Ascending)
    ): Flow<Resource<List<Group>>> {
        return groupRepository.getGroupsAsFlow()
            .map { groups ->
                when (groupOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (groupOrder) {
                            is GroupOrder.Name -> groups.sortedBy { it.name.lowercase() }
                        }
                    }

                    is OrderType.Descending -> {
                        when (groupOrder) {
                            is GroupOrder.Name -> groups.sortedByDescending { it.name.lowercase() }
                        }
                    }
                }
                Resource.Success(groups)
            }
    }
}