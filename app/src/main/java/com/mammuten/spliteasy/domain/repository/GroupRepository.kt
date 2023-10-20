package com.mammuten.spliteasy.domain.repository

import com.mammuten.spliteasy.domain.model.Bill
import com.mammuten.spliteasy.domain.model.Group
import com.mammuten.spliteasy.domain.model.Member
import com.mammuten.spliteasy.util.common.Resource
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun upsertGroup(group: Group)
    suspend fun deleteGroup(group: Group)

    suspend fun getAllGroups(): List<Group>

    fun getAllGroupsFlow(): Flow<List<Group>>

    suspend fun getGroupWithBills(groupId: Int): List<Bill>

    fun getGroupWithBillsFlow(groupId: Int): Flow<List<Bill>>

    suspend fun getGroupWithMembers(groupId: Int): List<Member>

    fun getGroupWithMembersFlow(groupId: Int): Flow<List<Member>>
}