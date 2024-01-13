package com.mammuten.spliteasy.data.source.local

import androidx.room.Dao
import androidx.room.Query
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralDao {
    @Query(
        "SELECT * FROM "
    )
    fun getMembersWithContributionsByBillId(billId: Int): Flow<Map<MemberEntity, ContributionEntity>>
}