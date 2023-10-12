package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.MemberMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.dto.Contribution
import com.mammuten.spliteasy.domain.model.dto.Member
import com.mammuten.spliteasy.domain.repository.MemberRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemberRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val memberMapper: MemberMapper
) : MemberRepository {
    override suspend fun upsertMember(member: Member) {
        val memberEntity = memberMapper.toEntity(member)
        db.memberDao().insertMember(memberEntity)
    }

    override suspend fun deleteMember(member: Member) {
        db.memberDao().deleteMember(memberMapper.toEntity(member))
    }

    override suspend fun getMemberWithContributions(memberId: Int): Resource<List<Contribution>> {
        TODO("Not yet implemented")
    }

    override fun getMemberWithContributionsFlow(memberId: Int): Flow<Resource<List<Contribution>>> {
        TODO("Not yet implemented")
    }
}