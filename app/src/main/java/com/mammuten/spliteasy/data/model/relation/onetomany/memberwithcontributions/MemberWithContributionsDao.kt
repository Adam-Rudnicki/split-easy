package com.mammuten.spliteasy.data.model.relation.onetomany.memberwithcontributions

import androidx.room.Query
import androidx.room.Transaction

interface MemberWithContributionsDao {
    @Transaction
    @Query("SELECT * FROM members")
    suspend fun getMembersWithContributions(): List<MemberWithContributions>

    @Transaction
    @Query("SELECT * FROM members WHERE id = :memberId")
    suspend fun getMemberWithContributions(memberId: Int): MemberWithContributions

}