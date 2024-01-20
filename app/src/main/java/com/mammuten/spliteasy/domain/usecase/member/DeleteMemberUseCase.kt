package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member

class DeleteMemberUseCase(
    private val memberRepo: MemberRepo
) {
    suspend operator fun invoke(member: Member) {
        memberRepo.deleteMember(member)
    }
}