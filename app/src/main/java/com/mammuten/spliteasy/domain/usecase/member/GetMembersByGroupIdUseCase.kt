package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.MemberOrder
import com.mammuten.spliteasy.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMembersByGroupIdUseCase(
    private val memberRepo: MemberRepo
) {
    operator fun invoke(
        groupId: Int,
        memberOrder: MemberOrder? = null
    ): Flow<List<Member>> {
        return memberRepo.getMembersByGroupId(groupId).map { members ->
            memberOrder?.let { order ->
                when (order) {
                    is MemberOrder.Name -> {
                        when (order.orderType) {
                            is OrderType.Ascending -> members.sortedBy { it.name.lowercase() }
                            is OrderType.Descending -> members.sortedByDescending { it.name.lowercase() }
                        }
                    }
                }
            } ?: members
        }
    }
}
