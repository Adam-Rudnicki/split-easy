package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asModel
import com.mammuten.spliteasy.data.mapper.asEntity
import com.mammuten.spliteasy.data.mapper.asModelList
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.Group
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GroupRepo(
    private val dataSource: LocalSplitDataSource
) {
    suspend fun upsertGroup(group: Group) = dataSource.upsertGroup(group.asEntity())

    suspend fun deleteGroup(group: Group) = dataSource.deleteGroup(group.asEntity())

    fun getGroupById(groupId: Int): Flow<Group?> =
        dataSource.getGroupById(groupId).map { it?.asModel() }

    fun getGroups(): Flow<List<Group>> = dataSource.getGroups().map { it.asModelList() }
}