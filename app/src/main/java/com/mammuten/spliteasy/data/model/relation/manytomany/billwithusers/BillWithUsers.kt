package com.mammuten.spliteasy.data.model.relation.manytomany.billwithusers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.associativeentity.billusercrossref.BillUserCrossRefEntity
import com.mammuten.spliteasy.data.model.entity.bill.BillEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

data class BillWithUsers(
    @Embedded val bill: BillEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(BillUserCrossRefEntity::class)
    )
    val users: List<MemberEntity>
)