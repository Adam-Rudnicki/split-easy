package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member

class UpsertMembersUseCase(
    private val memberRepo: MemberRepo
) {
    suspend operator fun invoke(
        membersToUpsert: List<Member>
    ) {
        memberRepo.upsertMembers(membersToUpsert)
    }
}