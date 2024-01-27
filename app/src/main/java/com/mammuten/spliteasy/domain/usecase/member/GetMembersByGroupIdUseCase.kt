package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.MemberOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMembersByGroupIdUseCase(
    private val memberRepo: MemberRepo
) {
    operator fun invoke(
        groupId: Int,
        memberOrder: MemberOrder = MemberOrder.NameAsc
    ): Flow<List<Member>> {
        return memberRepo.getMembersByGroupId(groupId).map { members ->
            val (withUserId, withoutUserId) = members.partition { it.userId != null }
            when (memberOrder) {
                is MemberOrder.NameAsc -> withUserId.sortedBy { it.name.lowercase() }
                    .plus(withoutUserId.sortedBy { it.name.lowercase() })

                is MemberOrder.NameDesc -> withUserId.sortedByDescending { it.name.lowercase() }
                    .plus(withoutUserId.sortedByDescending { it.name.lowercase() })
            }
        }
    }
}