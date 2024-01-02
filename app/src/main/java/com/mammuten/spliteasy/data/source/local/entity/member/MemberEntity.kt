package com.mammuten.spliteasy.data.source.local.entity.member

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.mammuten.spliteasy.data.source.local.entity.group.GroupEntity

@Entity(
    tableName = "members",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupId: Int,
    val nickname: String?,
)