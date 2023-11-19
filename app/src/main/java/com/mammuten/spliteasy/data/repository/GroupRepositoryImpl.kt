package com.mammuten.spliteasy.data.repository

import com.mammuten.spliteasy.data.mapper.GroupMapper
import com.mammuten.spliteasy.data.source.local.AppDatabase
import com.mammuten.spliteasy.domain.model.Group
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
) : GroupRepository {
    override suspend fun upsertGroup(group: Group) {
        withContext(appDispatchers.io) {
            db.groupDao().upsertGroups(groupMapper.toEntity(group))
        }
    }

    override suspend fun deleteGroup(group: Group) {
        withContext(appDispatchers.io) {
            db.groupDao().deleteGroups(groupMapper.toEntity(group))
        }
    }

    override suspend fun getGroupById(groupId: Int): Group {
        return withContext(appDispatchers.io) {
            val group = db.groupDao().loadGroupById(groupId)
            groupMapper.toDomain(group)
        }
    }
    override suspend fun getGroups(): List<Group> {
        return withContext(appDispatchers.io) {
            val groups = db.groupDao().loadGroups()
            groupMapper.toDomainList(groups)
        }
    }

    override fun getGroupsAsFlow(): Flow<List<Group>> {
        return db.groupDao().loadGroupsAsFlow()
            .map { groupMapper.toDomainList(it) }
            .flowOn(appDispatchers.io)
    }

}