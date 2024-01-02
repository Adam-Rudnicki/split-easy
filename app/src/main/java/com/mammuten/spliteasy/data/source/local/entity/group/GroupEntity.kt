package com.mammuten.spliteasy.data.source.local.entity.group

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = GroupEntity.TABLE_NAME)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String,
    val description: String? = null,
    val created: Date = Date()
) {
    companion object {
        const val TABLE_NAME = "groups"
    }
}
