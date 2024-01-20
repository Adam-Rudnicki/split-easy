package com.mammuten.spliteasy.data.source.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    val name: String,
    val surname: String? = null,
    val nick: String? = null,
    val phone: String? = null,
    val description: String? = null
) {
    companion object {
        const val TABLE_NAME = "users"
    }
}