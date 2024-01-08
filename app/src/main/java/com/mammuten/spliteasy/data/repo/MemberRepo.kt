package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asEntity
import com.mammuten.spliteasy.data.mapper.asMemberModelList
import com.mammuten.spliteasy.data.mapper.asModel
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MemberRepo(
    private val dataSource: LocalSplitDataSource
) {
    suspend fun upsertMember(member: Member) = dataSource.upsertMember(member.asEntity())

    suspend fun deleteMember(member: Member) = dataSource.deleteMember(member.asEntity())

    fun getMemberById(memberId: Int): Flow<Member?> =
        dataSource.getMemberById(memberId).map { it?.asModel() }

    fun getMembersByGroupId(groupId: Int): Flow<List<Member>> =
        dataSource.getMembersByGroupId(groupId).map { it.asMemberModelList() }
}