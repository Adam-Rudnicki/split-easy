package com.mammuten.spliteasy.data.source.local.entity.contribution

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity

@Entity(
    tableName = "contributions",
    primaryKeys = ["billId", "memberId"],
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class ContributionEntity(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Double,
    val amountOwed: Double,
)