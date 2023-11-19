package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {
    suspend fun upsertMember(member: Member)
    suspend fun deleteMember(member: Member)

    suspend fun getMembersByGroupId(groupId: Int): List<Member>

    fun getMembersByGroupIdAsFlow(groupId: Int): Flow<List<Member>>
}