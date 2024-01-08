package com.mammuten.spliteasy.data.source.local.entity.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberDao {
    @Upsert
    suspend fun upsertMember(memberEntity: MemberEntity)

    @Delete
    suspend fun deleteMember(memberEntity: MemberEntity)

    @Query("SELECT * FROM ${MemberEntity.TABLE_NAME} WHERE id = :memberId")
    fun getMemberById(memberId: Int): Flow<MemberEntity?>

    @Query("SELECT * FROM ${MemberEntity.TABLE_NAME} WHERE groupId = :groupId")
    fun getMembersByGroupId(groupId: Int): Flow<List<MemberEntity>>
}