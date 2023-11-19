package com.mammuten.spliteasy.domain.usecase.group

import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.domain.util.GroupOrder
import com.mammuten.spliteasy.domain.util.OrderType
import com.mammuten.spliteasy.util.common.Resource
import javax.inject.Inject

class GetGroups @Inject constructor(
    private val groupRepository: GroupRepository
) {
    suspend operator fun invoke(
        groupOrder: GroupOrder = GroupOrder.Name(OrderType.Ascending)
    ): Resource<List<Group>> {
        return groupRepository.getGroups()
            .let { groups ->
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
