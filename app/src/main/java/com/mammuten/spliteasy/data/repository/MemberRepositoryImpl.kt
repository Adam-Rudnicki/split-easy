package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.ContributionMapper
import com.mammuten.spliteasy.data.mapper.MemberMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Contribution
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
    private val contributionMapper: ContributionMapper
) : MemberRepository {
    override suspend fun upsertMember(member: Member) {
        withContext(appDispatchers.io) {
            db.memberDao().upsertMember(memberMapper.toEntity(member))
        }
    }

    override suspend fun deleteMember(member: Member) {
        withContext(appDispatchers.io) {
            db.memberDao().deleteMember(memberMapper.toEntity(member))
        }
    }

    override suspend fun getMemberWithContributions(memberId: Int): List<Contribution> {
        return withContext(appDispatchers.io) {
            val memberWithContributions =
                db.memberWithContributionsDao().getMemberWithContributions(memberId)
            contributionMapper.toDomainList(memberWithContributions.contributions)
        }
    }

    override fun getMemberWithContributionsFlow(memberId: Int): Flow<List<Contribution>> {
        return db.memberWithContributionsDao().getMemberWithContributionsFlow(memberId)
            .map { memberWithContributions ->
                contributionMapper.toDomainList(memberWithContributions.contributions)
            }.flowOn(appDispatchers.io)
    }
}