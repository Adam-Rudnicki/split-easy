package com.mammuten.spliteasy.data.source.local.entity.contribution

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.source.local.entity.bill.BillEntity
import com.mammuten.spliteasy.data.source.local.entity.member.MemberEntity

@Entity(
    tableName = ContributionEntity.TABLE_NAME,
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
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
data class ContributionEntity(
    val billId: Int,
    val memberId: Int,
    val amountPaid: Double = 0.0,
    val amountOwed: Double = 0.0
) {
    companion object {
        const val TABLE_NAME = "contributions"
    }
}