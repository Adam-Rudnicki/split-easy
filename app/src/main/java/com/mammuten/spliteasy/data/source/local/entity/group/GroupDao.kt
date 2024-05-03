package com.mammuten.spliteasy.data.source.local.entity.group

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {
    @Upsert
    suspend fun upsertGroup(groupEntity: GroupEntity)

    @Delete
    suspend fun deleteGroupHelper(groupEntity: GroupEntity)

    @Query("DELETE FROM ${BillEntity.TABLE_NAME} WHERE groupId = :groupId")
    suspend fun deleteBillsByGroupId(groupId: Int)

    @Transaction
    suspend fun deleteGroup(groupEntity: GroupEntity) {
        deleteBillsByGroupId(groupEntity.id!!)
        deleteGroupHelper(groupEntity)
    }

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME} WHERE id = :groupId")
    fun getGroupById(groupId: Int): Flow<GroupEntity?>

    @Query("SELECT * FROM ${GroupEntity.TABLE_NAME}")
    fun getGroups(): Flow<List<GroupEntity>>
}