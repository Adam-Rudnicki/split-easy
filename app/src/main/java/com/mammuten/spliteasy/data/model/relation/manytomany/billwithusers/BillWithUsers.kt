package com.mammuten.spliteasy.data.model.relation.manytomany.billwithusers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.associativeentity.billusercrossref.BillUserCrossRef
import com.mammuten.spliteasy.data.model.entity.bill.Bill
import com.mammuten.spliteasy.data.model.entity.user.User

data class BillWithUsers(
    @Embedded val bill: Bill,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(BillUserCrossRef::class)
    )
    val users: List<User>
)