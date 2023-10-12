package com.mammuten.spliteasy.data.source.local.model.entity.group

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class GroupEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
)
