package com.mammuten.spliteasy.data.source.local.entity

import androidx.room.Dao
import androidx.room.Query
import com.mammuten.spliteasy.data.source.local.entity.contribution.ContributionEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralDao {
    @Query(
        "SELECT * " +
                "FROM ${MemberEntity.TABLE_NAME} " +
                "JOIN ${ContributionEntity.TABLE_NAME} " +
                "ON ${MemberEntity.TABLE_NAME}.id = ${ContributionEntity.TABLE_NAME}.memberId " +
                "WHERE ${ContributionEntity.TABLE_NAME}.billId = :billId"
    )
    fun getMembersAndContributionsInBill(billId: Int): Flow<Map<MemberEntity, ContributionEntity>>

    @Query(
        "SELECT * " +
                "FROM ${MemberEntity.TABLE_NAME} " +
                "LEFT JOIN ${ContributionEntity.TABLE_NAME} " +
                "ON ${MemberEntity.TABLE_NAME}.id = ${ContributionEntity.TABLE_NAME}.memberId " +
                "AND ${ContributionEntity.TABLE_NAME}.billId = :billId " +
                "WHERE ${MemberEntity.TABLE_NAME}.groupId = :groupId "

    )
    fun getAllMembersInGroupAndContributionsInBill(
        groupId: Int, billId: Int
    ): Flow<Map<MemberEntity, ContributionEntity?>>
}