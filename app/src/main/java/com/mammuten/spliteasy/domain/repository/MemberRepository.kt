package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.domain.model.dto.Member
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow


interface MemberRepository {
    suspend fun upsertMember(member: Member)
    suspend fun deleteMember(member: Member)

    suspend fun getMemberWithContributions(memberId: Int): Resource<List<Contribution>>

    fun getMemberWithContributionsFlow(memberId: Int): Flow<Resource<List<Contribution>>>
}