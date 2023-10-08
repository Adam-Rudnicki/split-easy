package com.mammuten.spliteasy.data.model.entity.associativeentity.billusercrossref

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

@Entity(
    tableName = "bill_user_cross_ref",
    primaryKeys = ["billId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = BillEntity::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BillUserCrossRefEntity(
    val billId: Int,
    val userId: Int
)