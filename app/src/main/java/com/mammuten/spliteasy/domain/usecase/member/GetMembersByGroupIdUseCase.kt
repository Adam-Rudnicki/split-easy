package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.order.MemberOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//class GetMembersByGroupIdUseCase(
//    private val memberRepo: MemberRepo
//) {
//    operator fun invoke(
//        groupId: Int,
//        memberOrder: MemberOrder = MemberOrder.Name(OrderType.Ascending)
//    ): Flow<List<Member>> {
//        return memberRepo.getMembersByGroupId(groupId).map { members ->
//            val (withUserId, withoutUserId) = members.partition { it.userId != null }
//            when (memberOrder) {
//                is MemberOrder.Name -> {
//                    when (memberOrder.orderType) {
//                        is OrderType.Ascending -> withUserId.sortedBy { it.name.lowercase() }
//                            .plus(withoutUserId.sortedBy { it.name.lowercase() })
//
//                        is OrderType.Descending -> withUserId.sortedByDescending { it.name.lowercase() }
//                            .plus(withoutUserId.sortedByDescending { it.name.lowercase() })
//                    }
//                }
//            }
//        }
//    }
//}

class GetMembersByGroupIdUseCase(
    private val memberRepo: MemberRepo
) {
    operator fun invoke(
        groupId: Int,
        memberOrder: MemberOrder = MemberOrder.NameAscending
    ): Flow<List<Member>> {
        return memberRepo.getMembersByGroupId(groupId).map { members ->
            when (memberOrder) {
                is MemberOrder.NameAscending -> members.sortedBy { it.name.lowercase() }
                is MemberOrder.NameDescending-> members.sortedByDescending { it.name.lowercase() }
            }
        }
    }
}