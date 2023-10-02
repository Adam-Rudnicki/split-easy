package com.mammuten.spliteasy.data.model.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [
        Index(
            value = ["username"],
            unique = true
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "account_id") val accountId: String?,
    val username: String,
    val nickname: String?,
)