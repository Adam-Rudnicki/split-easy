package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member
import kotlinx.coroutines.flow.Flow

class GetMemberByIdUseCase(
    private val memberRepo: MemberRepo
) {
    operator fun invoke(memberId: Int): Flow<Member?> {
        return memberRepo.getMemberById(memberId)
    }
}