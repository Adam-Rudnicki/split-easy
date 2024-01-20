package com.mammuten.spliteasy.data.repo

import com.mammuten.spliteasy.data.mapper.asEntity
import com.mammuten.spliteasy.data.mapper.asModel
import com.mammuten.spliteasy.data.mapper.asUserModelList
import com.mammuten.spliteasy.data.source.local.LocalSplitDataSource
import com.mammuten.spliteasy.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepo(
    private val dataSource: LocalSplitDataSource
) {
    suspend fun upsertUser(user: User) = dataSource.upsertUser(user.asEntity())

    suspend fun deleteUser(user: User) = dataSource.deleteUser(user.asEntity())

    fun getUserById(userId: Int): Flow<User?> =
        dataSource.getUserById(userId).map { it?.asModel() }

    fun getUsers(): Flow<List<User>> = dataSource.getUsers().map { it.asUserModelList() }
}