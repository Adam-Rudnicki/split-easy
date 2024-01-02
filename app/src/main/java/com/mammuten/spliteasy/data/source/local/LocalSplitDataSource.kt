package com.mammuten.spliteasy.data.source.local

import com.mammuten.spliteasy.data.source.local.entity.group.GroupDao
import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity
import kotlinx.coroutines.flow.Flow

class LocalSplitDataSource(
    private val groupDao: GroupDao
) {
    suspend fun upsertGroup(groupEntity: GroupEntity) = groupDao.upsertGroup(groupEntity)
    suspend fun deleteGroup(groupEntity: GroupEntity) = groupDao.deleteGroup(groupEntity)
    fun getGroupById(groupId: Int): Flow<GroupEntity?> = groupDao.getGroupById(groupId)
    fun getGroups(): Flow<List<GroupEntity>> = groupDao.getGroups()
}