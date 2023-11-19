package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.MemberMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.repository.MemberRepository
import com.mammuten.spliteasy.util.common.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val appDispatchers: AppDispatchers,
    private val memberMapper: MemberMapper,
) : MemberRepository {
    override suspend fun upsertMember(member: Member) {
        withContext(appDispatchers.io) {
            db.memberDao().upsertMembers(memberMapper.toEntity(member))
        }
    }

    override suspend fun deleteMember(member: Member) {
        withContext(appDispatchers.io) {
            db.memberDao().deleteMembers(memberMapper.toEntity(member))
        }
    }

    override suspend fun getMembersByGroupId(groupId: Int): List<Member> {
        return withContext(appDispatchers.io) {
            val members = db.memberDao().loadMembersByGroupId(groupId)
            memberMapper.toDomainList(members)
        }
    }

    override fun getMembersByGroupIdAsFlow(groupId: Int): Flow<List<Member>> {
        return db.memberDao().loadMembersByGroupIdAsFlow(groupId)
            .map { memberMapper.toDomainList(it) }
            .flowOn(appDispatchers.io)
    }

}