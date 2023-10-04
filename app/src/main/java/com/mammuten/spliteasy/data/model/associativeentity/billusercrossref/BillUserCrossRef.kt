package com.mammuten.spliteasy.data.model.associativeentity.billusercrossref

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.user.User

@Entity(
    tableName = "bill_user_cross_ref",
    primaryKeys = ["billId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Bill::class,
            parentColumns = ["id"],
            childColumns = ["billId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BillUserCrossRef(
    val billId: Int,
    val userId: Int
)