package com.mammuten.spliteasy.data.model.relation.manytomany.userwithgroups

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

import com.mammuten.spliteasy.data.model.entity.associativeentity.groupusercrossref.GroupUserCrossRefEntity
import com.mammuten.spliteasy.data.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

data class UserWithGroups (
    @Embedded val user: MemberEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId",
        associateBy = Junction(GroupUserCrossRefEntity::class)
    )
    val groups: List<GroupEntity>
)