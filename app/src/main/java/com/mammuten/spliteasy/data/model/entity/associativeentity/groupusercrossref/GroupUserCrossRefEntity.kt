package com.mammuten.spliteasy.data.model.entity.associativeentity.groupusercrossref

import androidx.room.Entity
import androidx.room.ForeignKey

import com.mammuten.spliteasy.data.model.entity.group.GroupEntity
import com.mammuten.spliteasy.data.model.entity.user.MemberEntity

@Entity(
    tableName = "group_user_cross_ref",
    primaryKeys = ["groupId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
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
data class GroupUserCrossRefEntity (
    val groupId: Int,
    val userId: Int
)