package com.mammuten.spliteasy.data.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class MemberEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int?,
    val nickname: String?,
)