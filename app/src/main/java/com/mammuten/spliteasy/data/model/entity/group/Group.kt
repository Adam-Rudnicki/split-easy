package com.mammuten.spliteasy.data.model.entity.group

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
)
