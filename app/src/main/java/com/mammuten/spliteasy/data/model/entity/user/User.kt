package com.mammuten.spliteasy.data.model.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

import android.graphics.Bitmap

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val nickname: String?,
)