package com.mammuten.spliteasy.domain.usecase.member

import com.mammuten.spliteasy.data.repo.ContributionRepo
import com.mammuten.spliteasy.data.repo.MemberRepo
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.util.MemberHasContributions
import kotlinx.coroutines.flow.firstOrNull

class DeleteMemberUseCase(
    private val memberRepo: MemberRepo,
    private val contributionRepo: ContributionRepo
) {
    suspend operator fun invoke(member: Member) {
        val contributions = contributionRepo.getContributionsByMemberId(member.id!!).firstOrNull()
        if (contributions.isNullOrEmpty()) {
            memberRepo.deleteMember(member)
        } else {
            throw MemberHasContributions("Member has contributions")
        }
    }
}