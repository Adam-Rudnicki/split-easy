package com.mammuten.spliteasy.data.source.local.model.relation.onetomany.memberwithcontributions

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MemberWithContributionsDao {
    @Transaction
    @Query("SELECT * FROM members WHERE id = :memberId")
    suspend fun getMemberWithContributions(memberId: Int): MemberWithContributions

    @Transaction
    @Query("SELECT * FROM members WHERE id = :memberId")
    fun getMemberWithContributionsFlow(memberId: Int): Flow<MemberWithContributions>

}