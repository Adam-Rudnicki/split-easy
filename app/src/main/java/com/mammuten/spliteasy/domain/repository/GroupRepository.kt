package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun upsertGroup(group: Group)
    suspend fun deleteGroup(group: Group)
    suspend fun getGroupById(groupId: Int): Group
    suspend fun getGroups(): List<Group>
    fun getGroupsAsFlow(): Flow<List<Group>>

}