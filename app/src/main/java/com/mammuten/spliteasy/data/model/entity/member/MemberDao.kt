package com.mammuten.spliteasy.data.model.entity.member

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MemberDao {
    @Upsert
    suspend fun insertMember(member: MemberEntity)

    @Delete
    suspend fun deleteMember(member: MemberEntity)

    @Query("SELECT * FROM members WHERE id = :id")
    suspend fun loadMemberById(id: Int): MemberEntity

//    @Query("SELECT * FROM members WHERE id = :id")
//    fun loadMemberById(id: Int): Flow<Member>

}