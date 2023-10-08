package com.mammuten.spliteasy.data.model.relation.manytomany.groupwithusers

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.associativeentity.groupusercrossref.GroupUserCrossRefEntity
import com.mammuten.spliteasy.data.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

data class GroupWithUsers (
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        associateBy = Junction(GroupUserCrossRefEntity::class)
    )
    val users: List<MemberEntity>
)