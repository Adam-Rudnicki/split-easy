package com.mammuten.spliteasy.data.model.entity.bill

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import com.mammuten.spliteasy.data.model.entity.group.GroupEntity

@Entity(
    tableName = "bills",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            childColumns = ["groupId"],
            parentColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
        )
    ]
)
data class BillEntity (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val groupId: Int,
    val amount: Double,
)