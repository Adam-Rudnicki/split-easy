package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.mapper.GroupMapper
import com.mammuten.spliteasy.data.mapper.MemberMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.AppDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val appDispatchers: AppDispatchers,
    private val groupMapper: GroupMapper,
    private val billMapper: BillMapper,
    private val memberMapper: MemberMapper
) : GroupRepository {
    override suspend fun upsertGroup(group: Group) {
        withContext(appDispatchers.io) {
            db.groupDao().upsertGroup(groupMapper.toEntity(group))
        }
    }

    override suspend fun deleteGroup(group: Group) {
        withContext(appDispatchers.io) {
            db.groupDao().deleteGroup(groupMapper.toEntity(group))
        }
    }

    override suspend fun getAllGroups(): List<Group> {
        return withContext(appDispatchers.io) {
            val groups = db.groupDao().getAllGroups()
            groupMapper.toDomainList(groups)
        }
    }

    override fun getAllGroupsFlow(): Flow<List<Group>> {
        return db.groupDao().getAllGroupsFlow()
            .map { groups ->
                groupMapper.toDomainList(groups)
            }
            .flowOn(appDispatchers.io)
    }

    override suspend fun getGroupWithBills(groupId: Int): List<Bill> {
        return withContext(appDispatchers.io) {
            val groupWithBills = db.groupWithBillsDao().getGroupWithBills(groupId)
            billMapper.toDomainList(groupWithBills.bills)
        }
    }

    override fun getGroupWithBillsFlow(groupId: Int): Flow<List<Bill>> {
        return db.groupWithBillsDao().getGroupWithBillsFlow(groupId)
            .map { groupWithBills ->
                billMapper.toDomainList(groupWithBills.bills)
            }
            .flowOn(appDispatchers.io)
    }

    override suspend fun getGroupWithMembers(groupId: Int): List<Member> {
        return withContext(appDispatchers.io) {
            val groupWithMembers = db.groupWithMembersDao().getGroupWithMembers(groupId)
            memberMapper.toDomainList(groupWithMembers.members)
        }
    }

    override fun getGroupWithMembersFlow(groupId: Int): Flow<List<Member>> {
        return db.groupWithMembersDao().getGroupWithMembersFlow(groupId)
            .map { groupWithMembers ->
                memberMapper.toDomainList(groupWithMembers.members)
            }
            .flowOn(appDispatchers.io)
    }
}