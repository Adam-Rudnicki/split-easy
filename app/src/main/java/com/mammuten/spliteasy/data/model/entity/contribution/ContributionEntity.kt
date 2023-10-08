package com.mammuten.spliteasy.data.model.entity.contribution

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

@Entity(
    tableName = "contributions",
    primaryKeys = ["billId", "userId"],
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
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class ContributionEntity(
    val billId: Int,
    val userId: Int,
    val amountPaid: Double,
    val amountOwed: Double,
)