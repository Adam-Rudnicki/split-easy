package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.dto.Bill
import com.mammuten.spliteasy.domain.model.dto.Group
import com.mammuten.spliteasy.domain.model.dto.Member
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun upsertGroup(group: Group)
    suspend fun deleteGroup(group: Group)

    suspend fun getAllGroups(): Resource<List<Group>>

    fun getAllGroupsFlow(): Flow<Resource<List<Group>>>

    suspend fun getGroupWithBills(groupId: Int): Resource<List<Bill>>

    fun getGroupWithBillsFlow(groupId: Int): Flow<Resource<List<Bill>>>

    suspend fun getGroupWithMembers(groupId: Int): Resource<List<Member>>

    fun getGroupWithMembersFlow(groupId: Int): Flow<Resource<List<Member>>>
}