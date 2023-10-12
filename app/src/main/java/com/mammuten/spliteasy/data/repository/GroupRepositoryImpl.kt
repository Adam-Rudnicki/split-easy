package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.BillMapper
import com.mammuten.spliteasy.data.mapper.GroupMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.dto.Bill
import com.mammuten.spliteasy.domain.model.dto.Group
import com.mammuten.spliteasy.domain.model.dto.Member
import com.mammuten.spliteasy.domain.repository.GroupRepository
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.yield

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val groupMapper: GroupMapper,
    private val billMapper: BillMapper
) : GroupRepository {
    override suspend fun upsertGroup(group: Group) {
        val groupEntity = groupMapper.toEntity(group)
        db.groupDao().upsertGroup(groupEntity)
    }

    override suspend fun deleteGroup(group: Group) {
        db.groupDao().deleteGroup(groupMapper.toEntity(group))
    }

    override suspend fun getAllGroups(): Resource<List<Group>> {
        val groups = db.groupDao().getAllGroups()
        return Resource.Success(groups.map { groupMapper.toDomain(it) })
    }

    override fun getAllGroupsFlow(): Flow<Resource<List<Group>>> {
        return flow {
            db.groupDao().getAllGroupsFlow().collect { groups ->
                emit(Resource.Loading())
                emit(Resource.Success(groups.map { groupMapper.toDomain(it) }))
            }
        }
    }

    override suspend fun getGroupWithBills(groupId: Int): Resource<List<Bill>> {
        val groupWithBills = db.groupWithBillsDao().getGroupWithBills(groupId)
        return Resource.Success(groupWithBills.bills.map { billMapper.toDomain(it) })
    }

    override fun getGroupWithBillsFlow(groupId: Int): Flow<Resource<List<Bill>>> {
        return flow {
            db.groupWithBillsDao().getGroupWithBillsFlow(groupId).collect { groupWithBills ->
                emit(Resource.Loading())
                emit(Resource.Success(groupWithBills.bills.map { billMapper.toDomain(it) }))
            }
        }
    }

    override suspend fun getGroupWithMembers(groupId: Int): Resource<List<Member>> {
        TODO("Not yet implemented")
    }

    override fun getGroupWithMembersFlow(groupId: Int): Flow<Resource<List<Member>>> {
        TODO("Not yet implemented")
    }
}